import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineField extends Pane {
    private final double TILE_SIZE = 40;
    private Tile[][] field;
    private int totallyOpened = 0;
    private boolean endMessageShown = false;
    private boolean firstClick = true;

    public MineField(int X_TILES, int Y_TILES, int MINES) {
        field = new Tile[X_TILES][Y_TILES];
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = new Tile(x, y, TILE_SIZE);
                field[x][y] = tile;
                tile.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) this.open(tile, X_TILES, Y_TILES, MINES);
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

    private void mining(int X_TILES, int Y_TILES, int MINES, int xFirst, int yFirst) {
        int setMines = 0;

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

        for (int x = 0; x < X_TILES; x++)
            for (int y = 0; y < Y_TILES; y++) {
                Tile tile = field[x][y];
                if (!tile.isMined()) {
                    int bombs = 0;
                    List<Tile> neighbours = this.getNeighbours(tile, X_TILES, Y_TILES);
                    for (Tile t : neighbours)
                        if (t.isMined()) bombs++;
                    if (bombs > 0)
                        tile.setText(String.valueOf(bombs));
                }
            }
    }


    private List<Tile> getNeighbours(Tile tile, int X_TILES, int Y_TILES) {
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

    private void open(Tile tile, int X_TILES, int Y_TILES, int MINES) {
        if (firstClick) {
            mining(X_TILES, Y_TILES, MINES, tile.getxCoord(), tile.getyCoord());
            firstClick = false;
        }
        if (tile.isOpen() || tile.isFlagged())
            return;
        if (tile.isMined()) {
            for (Tile[] t : field)
                for (Tile e : t)
                    if (e.isMined()) {
                        e.setOpen();
                    }
            if (!endMessageShown) {
                ResultWindow.openResultWindow("Defeat!", "Game Over!");
                endMessageShown = true;
            }
            return;
        }

        tile.setOpen();

        totallyOpened++;
        if (tile.getText().isEmpty()) {
            for (Tile t : this.getNeighbours(tile, X_TILES, Y_TILES))
                this.open(t, X_TILES, Y_TILES, MINES);

        }
        if (totallyOpened == X_TILES * Y_TILES - MINES && !endMessageShown) {
            ResultWindow.openResultWindow("Victory!", "You Won!");
            endMessageShown = true;
        }
    }


    public double getTILE_SIZE() {
        return TILE_SIZE;
    }
}