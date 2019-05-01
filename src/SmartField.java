import javafx.scene.control.TextField;

class SmartField {

    static void process(TextField addTabField) {
        if (addTabField.getText().length() > 0) {

            String text = addTabField.getText();

            if (!(text.charAt(0) == '#')) {
                if (!Tabs.isTab(text)) {
                    Tabs tabs = Tabs.getTabs(text);
                    MainFX.getTabPane().getTabs().add(tabs.tab);
                    addTabField.setText("");
                } else {
                    addTabField.setText("");
                }
            } else {
                if ("settings".equals(text.substring(1))) {
                    if (!(Tabs.tabsList.size() == 0)) {
                        SettingsFX.start();
                        addTabField.setText("");
                    } else {
                        addTabField.setText("");
                    }
                } else if ("editable:false".equals(text.substring(1))) {
                    Tabs tabs = Tabs.getActiveTabs();
                    if (tabs != null) {
                        tabs.textArea.setEditable(false);
                    }
                    addTabField.setText("");
                } else if ("editable:true".equals(text.substring(1))) {
                    Tabs tabs1 = Tabs.getActiveTabs();
                    if (tabs1 != null) {
                        tabs1.textArea.setEditable(true);
                    }
                    addTabField.setText("");
                } else {
                    addTabField.setText("");
                }
            }
        }
    }
}
