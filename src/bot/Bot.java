package bot;

import game.MineField;
import game.Tile;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.*;


public class Bot {
    //Поле, с которым работает бот
    private MineField game;
    //Массив ячеек из этого поля
    private Tile[][] field;
    //Множество неоткрытых/непомеченных ячеек поля (чтобы не проверять зазря уже октрытые)
    private Set<Tile> tiles = new HashSet<>();
    private boolean work = false;

    public Bot(MineField game, int X_TILES, int Y_TILES) {
        this.game = game;
        this.field = game.getField();
        for (int x = 0; x < X_TILES; x++)
            tiles.addAll(Arrays.asList(field[x]));
    }
    //Окно для бота
    public void openBotWindow() {
        Stage botWindow = new Stage();
        //Кнопка запуска бота
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            work = true;
            minesweeping();
        });
        //Кнопка остановки бота
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> work = false);
        //Кнопка выхода из окна бота
        Button closeButton = new Button("Exit");
        closeButton.setOnAction(e -> botWindow.close());

        HBox botButtons = new HBox(startButton, stopButton, closeButton);
        botButtons.setAlignment(Pos.CENTER);
        botButtons.setSpacing(5);

        BorderPane box = new BorderPane();
        box.setCenter(botButtons);
        box.setPrefHeight(75);
        box.setPrefWidth(200);
        Scene botScene = new Scene(box);
        botWindow.setTitle("Bot");
        botWindow.setResizable(false);
        botWindow.setScene(botScene);
        botWindow.show();
    }

    //Рабочий цикл бота с алгоритмами
    private void minesweeping() {
        while (!game.isEndMessageShown() && work) {

        }
    }


}
