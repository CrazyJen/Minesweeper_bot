package bot;

import bot.support.BotGroup;
import game.MineField;
import game.Tile;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Bot {
    //Поле, с которым работает бот
    private MineField game;
    //Массив ячеек из этого поля
    private Tile[][] field;
    private ArrayList<BotGroup> groups = new ArrayList<>();

    public Bot(MineField game, int X_TILES, int Y_TILES) {
        this.game = game;
        this.field = game.getField();
    }

    //Окно для бота
    public void openBotWindow() {
        Stage botWindow = new Stage();
        //Кнопка запуска бота
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> minesweeping());
        //Кнопка выхода из окна бота
        Button closeButton = new Button("Exit");
        closeButton.setOnAction(e -> botWindow.close());

        HBox botButtons = new HBox(startButton, closeButton);
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
        if (!game.isEndMessageShown()) {
            if (game.isFirstClick()) {
                game.open(field[0][0]);
            } else {
                setGroups();
                for (BotGroup gr : groups) {
                    if (gr.getMines() == gr.size()) {
                        for (Tile tile : gr.getTiles())
                            tile.flag(true);
                    } else if (gr.getMines() == 0) {
                        for (Tile tile : gr.getTiles())
                            game.open(tile);
                    } else {
                        //Если нету групп с 0 минами
                        for (Tile tile : findMin().getTiles()) {
                            game.open(tile);
                        }
                    }
                }
            }
        }
    }

    private BotGroup findMin() {
        BotGroup min = null;
        int minMines = Integer.MAX_VALUE;

        for (BotGroup gr : groups) {
            if (gr.getMines() < minMines && nonFlagged(gr)) {
                min = gr;
                minMines = gr.getMines();
            }
        }
        return min;
    }

    private boolean nonFlagged(BotGroup gr) {
        for (Tile tile : gr.getTiles())
            if (tile.isFlagged()) return false;
        return true;
    }

    //Создает список групп ячеек, связанных одним значением открытого поля, а также разбивает их на более мелкие, удаляет повторяющиеся.
    private void setGroups() {
        int width = game.getX_TILES();
        int height = game.getY_TILES();
        groups.clear();
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                field[x][y].setGroup(game, groups); // создание групп
        boolean repeat;
        do {
            repeat = false;
            for (int i = 0; i < groups.size() - 1; i++) {  // проходим по списку групп
                BotGroup botGroupI = groups.get(i);
                for (int j = i + 1; j < groups.size(); j++) {   // сравниваем ее с остальными меньшими группами
                    BotGroup botGroupJ = groups.get(j);
                    if (botGroupI.equals(botGroupJ)) {                  // удаляем одинаковые группы
                        groups.remove(j--);
                        break;
                    }
                    BotGroup parent;                               // большая группа
                    BotGroup child;                                // меньшая группа
                    if (botGroupI.size() > botGroupJ.size()) {            // определяем большую и меньшую группы по кол-ву ячеек
                        parent = botGroupI;
                        child = botGroupJ;
                    } else {
                        child = botGroupI;
                        parent = botGroupJ;
                    }
                    if (parent.contains(child)) {               // если большая содержит меньшую
                        parent.subtraction(child);              //  то вычитаем меньшую из большей
                        repeat = true;                            //  фиксируем факт изменения групп
                    } else if (botGroupI.overlaps(botGroupJ)) {    // иначе если группы пересекаются
                        if (botGroupI.getMines() > botGroupJ.getMines()) { // определяем большую и меньшую группы по кол-ву мин
                            parent = botGroupI;
                            child = botGroupJ;
                        } else {
                            child = botGroupI;
                            parent = botGroupJ;
                        }
                        BotGroup overlap = parent.getOverlap(child);// то берем результат пересечения
                        if (overlap != null) {                  //  и если он имеет смысл (в результате пересечения выявились ячейки с 0% или 100%)
                            groups.add(overlap);                //  то вносим соответствующие коррективы в список
                            parent.subtraction(overlap);
                            child.subtraction(overlap);
                            repeat = true;
                        }
                    }
                }
            }
        }
        while (repeat);
        ArrayList<BotGroup> clearGroups = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).size() > 0)
                clearGroups.add(groups.get(i));
        }
        groups = clearGroups;
    }

}
