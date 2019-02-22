package game;

import bot.Bot;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {
    //Получение настроек
    private Settings settings = new Settings();


    private Parent content() {
        BorderPane root = new BorderPane();
        //Создание объектов "Игровое поле" и "Бот"
        MineField field = new MineField(settings.getX_TILES(), settings.getY_TILES(), settings.getMINES());
        Bot bot = new Bot(field, settings.getX_TILES(), settings.getY_TILES());

        // Создание тулбара с кнопками
        Button newGameButton = new Button("New game");
        newGameButton.setOnAction(e -> newGame());
        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> settings.openSettings());
        Button botButton = new Button("Bot");
        botButton.setOnAction(e -> bot.openBotWindow());
        ToolBar toolBar = new ToolBar(newGameButton, settingsButton, botButton);

        root.setTop(toolBar);
        root.setCenter(field);
        root.setPrefHeight(field.getTILE_SIZE() * settings.getX_TILES() + 35);
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
        scene.getWindow();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
