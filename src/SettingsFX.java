import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import lang.Dialogs;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

class SettingsFX {

    private static Stage stage = new Stage();
    private static AnchorPane anchorPane = new AnchorPane();
    private static Scene scene = new Scene(anchorPane,140,100);
    private static Slider slider = new Slider();
    private static ColorPicker colorPicker = new ColorPicker();
    private static ColorPicker colorPicker1 = new ColorPicker();

    static {
        stage.setTitle(Dialogs.PERSONALIZATION);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
        anchorPane.getChildren().add(colorPicker);
        anchorPane.getChildren().add(colorPicker1);
        anchorPane.getChildren().add(slider);
        colorPicker1.setLayoutY(40);
        slider.setLayoutY(80);
        slider.setValue(Tools.getAddTabField().getPrefWidth() / 5);
        colorPicker.setOnAction(event -> {
            Tabs tabs = Tabs.getActiveTabs();
            if (tabs != null) {
                tabs.changeToolsColor(colorPicker.getValue());
            }
        });
        colorPicker1.setOnAction(event -> {
            Tabs tabs = Tabs.getActiveTabs();
            if (tabs != null) {
                tabs.changeAreaColor(colorPicker1.getValue());
            }
        });
        slider.setOnMousePressed(event -> Tools.getAddTabField().setPrefWidth(slider.getValue() * 5));
        stage.setScene(scene);
    }

    static void start() {
        if (!stage.isShowing()) {
            Tabs tabs = Tabs.getActiveTabs();
            if (tabs != null) {
                colorPicker.setValue(Tabs.getActiveTabs().toolsColor);
                colorPicker1.setValue(Tabs.getActiveTabs().areaColor);
                stage.show();
            }
        }
    }
}
