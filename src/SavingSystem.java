import lang.Dialogs;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import libs.Decrypt;
import libs.Encrypt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.*;


class SavingSystem {

    private static List<String> filesForDeleting = new ArrayList<>();

    static void saveChanges() {

        deleteDeletedFiles();

        //////////////////////////////////////////////////////////////////////////////////////
        List<Map<String,String>> list = new ArrayList<>();
        for (Tabs tabs : Tabs.tabsList) {
            Map<String,String> tabsMap = new HashMap<>();
            tabsMap.put("name", (tabs.tab.getText()));
            tabsMap.put("fontSize",tabs.textArea.getFont().getSize() + "");
            try {
                tabsMap.put("content", Encrypt.encrypt(tabs.textArea.getText(), Main.ecipher));
            } catch (Exception ignored) {}
            tabsMap.put("toolsColorRED",tabs.toolsColor.getRed()+"");
            tabsMap.put("toolsColorGREEN",tabs.toolsColor.getGreen()+"");
            tabsMap.put("toolsColorBLUE",tabs.toolsColor.getBlue()+"");
            tabsMap.put("opacityToolsColor",tabs.toolsColor.getOpacity() + "");
            tabsMap.put("areaColorRED",tabs.areaColor.getRed()+"");
            tabsMap.put("areaColorGREEN",tabs.areaColor.getGreen()+"");
            tabsMap.put("areaColorBLUE",tabs.areaColor.getBlue()+"");
            tabsMap.put("opacityAreaColor",tabs.areaColor.getOpacity() + "");
            tabsMap.put("isEditable",tabs.textArea.isEditable() + "");
            tabsMap.put("fieldLength",Tools.getAddTabField().getPrefWidth() + "");
            list.add(tabsMap);
        }
        for (Map<String,String> map : list) {
            setMapFile(map,new File(Dialogs.SYSTEM_PATH+"#TAB " + map.get("name")));
        }
        ///////////////////////////////////////////////////////////////////////////////////////
        Map<String,String> sysFile = new HashMap<>();
        sysFile.put("welcomeTab",MainFX.getWelcomeTab() + "");
        sysFile.put("stageWidth", (int) MainFX.getScene().getWidth() + "");
        sysFile.put("stageHeight", (int) MainFX.getScene().getHeight() + "");
        setMapFile(sysFile,new File(Dialogs.SYSTEM_PATH+"SysFile"));
        ///////////////////////////////////////////////////////////////////////////////////////
    }
    static void loadChanges() {

        ///////////////////////////////////////////////////////////////////////////////////////
        List<File> fileList = fileTabList();
        List<Map<String,String>> list = new ArrayList<>();
        for (File f : fileList) {
            list.add(getMapFile(f));
        }

        for (Map<String,String> map : list) {
            Tabs tabs = Tabs.getTabs(map.get("name"));
            try {
                tabs.textArea.setText(Decrypt.decrypt(map.get("content"), Main.dcipher));
            } catch (Exception ex) {
                tabs.textArea.setText(Dialogs.HAS_ERROR_DURING_DECRYPTING);
                MainFX.getTabPane().getTabs().add(tabs.tab);
                Tabs.tabsList.remove(tabs);
                continue;
            }
            try {
                tabs.textArea.setFont(new Font(Double.parseDouble(map.get("fontSize"))));
                tabs.toolsColor = new Color(Double.parseDouble(map.get("toolsColorRED")), Double.parseDouble(map.get("toolsColorGREEN")),
                        Double.parseDouble(map.get("toolsColorBLUE")), Double.parseDouble(map.get("opacityToolsColor")));
                tabs.areaColor = new Color(Double.parseDouble(map.get("areaColorRED")), Double.parseDouble(map.get("areaColorGREEN")),
                        Double.parseDouble(map.get("areaColorBLUE")), Double.parseDouble(map.get("opacityAreaColor")));
                tabs.textArea.setEditable(Boolean.parseBoolean(map.get("isEditable")));
                Tools.getAddTabField().setPrefWidth(Double.parseDouble(map.get("fieldLength")));
            } catch (Exception ex) {
                tabs.textArea.setText(Dialogs.MAP_FILE_IS_NOT_VALID);
                MainFX.getTabPane().getTabs().add(tabs.tab);
                Tabs.tabsList.remove(tabs);
            }
        }
        for (Tabs tabs : Tabs.tabsList) {
            MainFX.getTabPane().getTabs().add(tabs.tab);
        }
        /////////////////////////////////////////////////////////////////////////////////////////
        Map<String,String> map = getMapFile(new File(Dialogs.SYSTEM_PATH+"SysFile"));
        if (map.size() != 0) {
            try {
                MainFX.setWelcomeTab(Boolean.parseBoolean(map.get("welcomeTab")));
                MainFX.setWidth(Integer.parseInt(map.get("stageWidth")));
                MainFX.setHeight(Integer.parseInt(map.get("stageHeight")));
            } catch (Exception ex) {
                Tabs tabs = Tabs.getTabs(Dialogs.FILE_OF_PROGRAM_IS_NOT_VALID);
                tabs.textArea.setText(Dialogs.CANNOT_READ_SYS_FILE);
                MainFX.getTabPane().getTabs().add(tabs.tab);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////////////
    }

    private static Map<String,String> getMapFile(File file) {
        Map<String,String> stringStringMap = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(fileInputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                StringBuilder leftLine = new StringBuilder();
                StringBuilder rightLine = new StringBuilder();
                boolean switchable = false;
                for (int i = 0; i < line.length(); i++) {
                    if (switchable) {
                        rightLine.append(line.charAt(i));
                    } else {
                        if (line.charAt(i) == ':') {
                            switchable = true;
                            continue;
                        }
                        leftLine.append(line.charAt(i));
                    }
                }
                stringStringMap.put(leftLine.toString(), rightLine.toString());
            }
        } catch (Exception ignored) {}
        return stringStringMap;
    }

    private static void setMapFile(Map<String,String> stringStringMap, File file) {

        Set<String> strings = stringStringMap.keySet();
        Iterator<String> stringIterator = strings.iterator();
        try {
            FileWriter fileWriter = new FileWriter(file);
            StringBuilder buffer = new StringBuilder();

            while (stringIterator.hasNext()) {
                String next = stringIterator.next();
                buffer.append(next).append(":").append(stringStringMap.get(next)).append("\n");
            }
            buffer = new StringBuilder(buffer.substring(0, buffer.length() - 1));
            fileWriter.write(buffer.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception ignored) {}
    }

    private static List<File> fileList() {
        File file = new File(Dialogs.SYSTEM_PATH);
        File[] files = file.listFiles();
        List<File> fileList = new ArrayList<>();
        for (File file1 : files) {
            if (!file1.isDirectory()) {
                fileList.add(file1);
            }
        }
        return fileList;
    }
    private static List<File> fileTabList() {
        List<File> list = fileList();
        List<File> get = new ArrayList<>();
        for (File f : list) {
            if (f.getName().substring(0,4).equals("#TAB")) {
                get.add(f);
            }
        }
        return get;
    }
    private static void deleteDeletedFiles() {
        for (String s : filesForDeleting) {
            File file = new File(Dialogs.SYSTEM_PATH+s);
            if (file.exists()) {
                if (!file.delete()) {
                    System.out.println("AN ERROR DURING DELETING FILE");
                }
            }
        }
    }
    static void markToDelete(String name) {
        filesForDeleting.add(name);
    }
}
