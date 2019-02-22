package game;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Игровое поле
public class MineField extends Pane {
    private final double TILE_SIZE = 40;
    //Матрица ячеек поля
    private Tile[][] field;
    //Счётчик открытых ячеек на поле
    private int totallyOpened = 0;
    //Параметр, показывающий закончена ли игра
    private boolean endMessageShown = false;
    //Параметр, определяющий является ли открытая ячейка первой открытой в данной партии
    //(Чтобы не бабахнуться с первого клика)
    private boolean firstClick = true;
    //Размеры поля и кол-во мин (подаётся из объёкта Settings)
    private int X_TILES, Y_TILES, MINES;

    //Конструктор
    //Добавление ячеек в массив поля и "навшивание" на каждую слушателя кликов мышки
    public MineField(int X_TILES, int Y_TILES, int MINES) {
        this.X_TILES = X_TILES;
        this.Y_TILES = Y_TILES;
        this.MINES = MINES;
        field = new Tile[X_TILES][Y_TILES];
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = new Tile(x, y, TILE_SIZE);
                field[x][y] = tile;
                tile.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) this.open(tile);
                    if (e.getButton() == MouseButton.SECONDARY)
                        if (!tile.isOpen()) {
                            if (tile.isFlagged()) {
                                tile.setFlagged(false);
                                tile.setQuestion(true);
                            } else if (tile.hasQuestion()) tile.setQuestion(false);
                            else tile.setFlagged(true);
                        }
                });
                getChildren().add(tile);
            }
        }
    }

    //Минирование поля (при открытии первой ячейки)
    private void mining(int xFirst, int yFirst) {
        int setMines = 0;

        //Выбор рандомной ячейки на поле и минирование (если это не первая открытая ячейка)
        while (setMines < MINES) {
            Random random = new Random();
            int x = random.nextInt(X_TILES);
            int y = random.nextInt(Y_TILES);
            if (x != xFirst && y != yFirst) {
                Tile tile = field[x][y];
                if (!tile.isMined()) {
                    tile.setMined();
                    setMines++;
                }
            }
        }

        //Проставление количества "опасных" соседей в ячейки
        for (int x = 0; x < X_TILES; x++)
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = field[x][y];
                if (!tile.isMined()) {
                    int bombs = 0;
                    List<Tile> neighbours = this.getNeighbours(tile);
                    for (Tile t : neighbours)
                        if (t.isMined()) bombs++;
                    if (bombs > 0)
                        tile.setText(String.valueOf(bombs));
                }
            }
    }

    //Функция для получения всех соседей клетки
    private List<Tile> getNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>();
        int x = tile.getxCoord();
        int y = tile.getyCoord();

        if (y - 1 >= 0) neighbours.add(field[x][y - 1]);
        if (y + 1 < Y_TILES) neighbours.add(field[x][y + 1]);
        if (x - 1 >= 0) neighbours.add(field[x - 1][y]);
        if (x + 1 < X_TILES) neighbours.add(field[x + 1][y]);
        if (y - 1 >= 0 && x - 1 >= 0) neighbours.add(field[x - 1][y - 1]);
        if (y - 1 >= 0 && x + 1 < X_TILES) neighbours.add(field[x + 1][y - 1]);
        if (y + 1 < Y_TILES && x - 1 >= 0) neighbours.add(field[x - 1][y + 1]);
        if (y + 1 < Y_TILES && x + 1 < X_TILES) neighbours.add(field[x + 1][y + 1]);
        return neighbours;
    }

    //Функция для открытия клетки
    private void open(Tile tile) {
        //Если это первая открытая ячейка - минируем поле
        if (firstClick) {
            mining(tile.getxCoord(), tile.getyCoord());
            firstClick = false;
        }

        //Если ячейка уже открыта или помечена игроком, как "опасная" - не делаем ничего
        if (tile.isOpen() || tile.isFlagged())
            return;
        //Если ячейка не открыта и заминирована - делаем "БАБАХ" и открываем окно с сообщением о проигрыше
        if (tile.isMined()) {
            for (Tile[] t : field)
                for (Tile e : t)
                    if (e.isMined()) {
                        e.setOpen();
                    }
            if (!endMessageShown) {
                endMessageShown = true;
                ResultWindow.openResultWindow("Defeat!", "Game Over!");
            }
            return;
        }

        tile.setOpen();

        totallyOpened++;
        if (tile.getText().isEmpty()) {
            for (Tile t : this.getNeighbours(tile))
                this.open(t);

        }
        //После каждой открытой клетки проверяем их кол-во
        //Если открыты все ячейки, кроме "опасных" - открываем окно с сообщением о победе
        if (totallyOpened == X_TILES * Y_TILES - MINES && !endMessageShown) {
            endMessageShown = true;
            ResultWindow.openResultWindow("Victory!", "You Won!");
        }
    }

    //Геттеры
    public double getTILE_SIZE() {
        return TILE_SIZE;
    }

    public boolean isEndMessageShown() {
        return endMessageShown;
    }

    public boolean isFirstClick() {
        return firstClick;
    }

    public int getX_TILES() {
        return X_TILES;
    }

    public int getY_TILES() {
        return Y_TILES;
    }

    public int getMINES() {
        return MINES;
    }

    public Tile[][] getField() {
        return field;
    }
}