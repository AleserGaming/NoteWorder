import lang.Dialogs;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainFX extends Application {

    private static TabPane tabPane = new TabPane();
    private static Stage primaryStage;
    private static Scene scene;
    private static boolean welcomeTab = true;
    private static int width = 1200;
    private static int height = 600;
    private static boolean personalSecretKey = false;

    @Override
    public void start(Stage primaryStage) {
        SavingSystem.loadChanges();
        welcomeTab();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("assets/icon.jpg")));
        tabPane.setSide(Side.LEFT);
        BorderPane root = new BorderPane();
        scene = new Scene(root, width,height);
        Image image = new Image(getClass().getResourceAsStream("assets/plus.png"));
        Tools.getZoomIn().setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Image image2 = new Image(getClass().getResourceAsStream("assets/minus.png"));
        Tools.getZoomOut().setBackground(new Background(new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Image image3 = new Image(getClass().getResourceAsStream("assets/paste.png"));
        Tools.getPaste().setBackground(new Background(new BackgroundImage(image3,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Image image4 = new Image(getClass().getResourceAsStream("assets/copy.png"));
        Tools.getCopyAll().setBackground(new Background(new BackgroundImage(image4,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Image image5 = new Image(getClass().getResourceAsStream("assets/erase.png"));
        Tools.getClearTab().setBackground(new Background(new BackgroundImage(image5,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));

        MainFX.primaryStage = primaryStage;

        primaryStage.setTitle(Dialogs.PROGRAM_NAME);

        root.setBottom(Tools.getToolBar());
        root.setCenter(tabPane);

        primaryStage.setScene(scene);

        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            SavingSystem.saveChanges();
            System.exit(0);
        });
    }

    private static void welcomeTab() {
        if (welcomeTab) {
            Tabs tabs = Tabs.getTabs("Welcome to NoteWorder!");
            tabs.textArea.setText(Dialogs.WELCOME_TEXT);
            tabs.textArea.setEditable(false);
            MainFX.getTabPane().getTabs().add(tabs.tab);
            welcomeTab = false;
        }
    }

    static TabPane getTabPane() {
        return tabPane;
    }
    static Stage getPrimaryStage() {
        return primaryStage;
    }
    static Scene getScene() {
        return scene;
    }
    static boolean getWelcomeTab() {
        return welcomeTab;
    }
    static void setWelcomeTab(boolean bool) {
        welcomeTab = bool;
    }
    static void setWidth(int width1) {
        width = width1;
    }
    static void setHeight(int height1) {
        height = height1;
    }
    static void setPersonalSecretKey(boolean bool) {
        personalSecretKey = bool;
    }
}
