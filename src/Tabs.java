import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

class Tabs {

    Tab tab = new Tab();
    TextArea textArea = new TextArea();
    Color toolsColor = Color.LIGHTGRAY;
    Color areaColor = Color.CYAN;

    static List<Tabs> tabsList = new ArrayList<>();

    static Tabs getTabs(String tabsName) {
        Tabs tabs = new Tabs();
        tabs.tab.setText(tabsName);
        tabs.tab.setContent(tabs.textArea);
        tabs.textArea.setWrapText(true);
        tabs.tab.setOnClosed(event -> {
            tabsList.remove(tabs);
            SavingSystem.markToDelete("#TAB " + tabs.tab.getText());
            Tools.getToolBar().setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        tabs.tab.setOnSelectionChanged(event -> {
            if (tabs.tab.isSelected()) {
                tabs.changeToolsColor(tabs.toolsColor);
                tabs.changeAreaColor(tabs.areaColor);
            }
        });

        tabs.textArea.setFont(new Font(26.5));
        tabsList.add(tabs);
        return tabs;
    }

    void changeToolsColor(Color color) {
        toolsColor = color;
        Tools.getToolBar().setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    void changeAreaColor(Color color) {
        areaColor = color;
        textArea.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    static Tabs getActiveTabs() {
        for (Tabs tabs : tabsList) {
            if (tabs.tab.isSelected()) {
                return tabs;
            }
        }
        return null;
    }

    private static Tabs getSerchedTabs(String tabName) {
        for (Tabs tabs : Tabs.tabsList) {
            if (tabs.tab.getText().equals(tabName)) {
                return tabs;
            }
        }
        return null;
    }
    static boolean isTab(String tabName) {
        return getSerchedTabs(tabName) != null;
    }
}
