import javafx.scene.effect.Effect;
import lang.Dialogs;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;


class Tools {

    private static ToolBar toolBar = new ToolBar();
    private static TextField addTabField = new TextField();
    private static Button zoomIn = new Button("  ");
    private static Button zoomOut = new Button("  ");
    private static Button clearTab = new Button("  ");
    private static Button copyAll = new Button("  ");
    private static Button paste = new Button("  ");
    private static CheckBox checkBox = new CheckBox(Dialogs.SHOWING_ON_ALL_WINDOWS);
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static DataFlavor flavor = DataFlavor.stringFlavor;

    static {
        addTabField.setFont(new Font(10));
        addTabField.setStyle("-fx-background-color: #555555; -fx-border-color: #000000");
        addTabField.setFocusTraversable(false);
        addTabField.setPrefWidth(500);
        addTabField.setPromptText(Dialogs.TAB_NAME);
        checkBox.setFocusTraversable(false);

        toolBar.getItems().add(zoomIn);
        toolBar.getItems().add(zoomOut);
        toolBar.getItems().add(addTabField);
        toolBar.getItems().add(clearTab);
        toolBar.getItems().add(copyAll);
        toolBar.getItems().add(paste);
        toolBar.getItems().add(checkBox);

        zoomIn.setOnAction(event -> {
            Tabs tabs = Tabs.getActiveTabs();
            double size;
            if (tabs != null) {
                size = tabs.textArea.getFont().getSize();
                size += 0.5;
                tabs.textArea.setFont(new Font(size));
            }
        });
        zoomOut.setOnAction(event -> {
            Tabs tabs = Tabs.getActiveTabs();
            double size;
            if (tabs != null) {
                size = tabs.textArea.getFont().getSize();
                size -= 0.5;
                tabs.textArea.setFont(new Font(size));
            }
        });
        clearTab.setOnAction(event -> {
            Tabs tabs = Tabs.getActiveTabs();
            if (tabs != null) {
                tabs.textArea.setText("");
            }

        });
        addTabField.setOnAction(event -> SmartField.process(addTabField));

        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                MainFX.getPrimaryStage().setAlwaysOnTop(true);
            } else {
                MainFX.getPrimaryStage().setAlwaysOnTop(false);
            }
        });
        copyAll.setOnAction(event -> {
            Tabs tabs = Tabs.getActiveTabs();
            if (tabs != null) {
                clipboard.setContents(new StringSelection(tabs.textArea.getText()), null);
            }
        });
        paste.setOnAction(event -> {
            Tabs tabs = Tabs.getActiveTabs();
            if (tabs != null) {
                if (clipboard.isDataFlavorAvailable(flavor)) {
                    try {
                        tabs.textArea.setText(tabs.textArea.getText() + clipboard.getData(flavor).toString());
                    } catch (Exception ignore) {
                    }
                }
            }
        });
    }

    static ToolBar getToolBar() {
        return toolBar;
    }
    static Button getZoomIn() {
        return zoomIn;
    }
    static Button getZoomOut() {
        return zoomOut;
    }
    static TextField getAddTabField() {
        return addTabField;
    }
    static Button getPaste() {
        return paste;
    }
    static Button getCopyAll() { return copyAll; }
    static Button getClearTab() { return clearTab; }
}
