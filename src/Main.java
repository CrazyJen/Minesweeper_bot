import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {
    private Settings settings = new Settings();

    private Parent content() {
        BorderPane root = new BorderPane();
        MineField field = new MineField(settings.getX_TILES(), settings.getY_TILES(), settings.getMINES());

        Button newGameButton = new Button("New game");
        newGameButton.setOnAction(e -> newGame());
        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> settings.openSettings());
        Button botButton = new Button("Bot");
        ToolBar toolBar = new ToolBar(newGameButton, settingsButton, botButton);

        root.setTop(toolBar);
        root.setCenter(field);
        root.setPrefHeight(field.getTILE_SIZE() * (settings.getX_TILES() + 1));
        root.setPrefWidth(field.getTILE_SIZE() * settings.getY_TILES());
        return root;
    }

    private Scene scene = new Scene(content());

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hexagonal Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void newGame() {
        scene.setRoot(content());
        scene.getWindow().sizeToScene();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
