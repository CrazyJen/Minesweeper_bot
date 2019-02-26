package game;

import bot.support.BotGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Tile extends StackPane {
    //Положение ячейки на поле
    private int xCoord, yCoord;
    //Открыта ли ячейка
    private boolean isOpen = false;
    //Заминирована ли ячейка
    private boolean isMined = false;
    //Помечена ли ячейка пользователем как заминированная
    private boolean isFlagged = false;
    //Помечена ли ячейка пользователем, как "непонятная"
    private boolean hasQuestion = false;
    //Хрень для графики
    private Circle border;
    private Text text = new Text();
    private ImageView flag = new ImageView(new Image("flag.png"));
    private ImageView mine = new ImageView(new Image("mine.png"));
    private ImageView question = new ImageView(new Image("question.png"));

    //Геттеры
    public Tile(int xCoord, int yCoord, double TILE_SIZE) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.border = new Circle(TILE_SIZE / 2 - 2);

        border.setFill(Color.GRAY);
        border.setStroke(Color.BLACK);


        text.setFont(Font.font(18));
        text.setVisible(false);

        flag.setVisible(false);
        flag.setFitHeight(25);
        flag.setFitWidth(25);
        mine.setVisible(false);
        mine.setFitHeight(25);
        mine.setFitWidth(25);
        question.setVisible(false);
        question.setFitHeight(25);
        question.setFitWidth(25);

        getChildren().addAll(border, text, flag, question, mine);

        double translateY = xCoord * (TILE_SIZE);
        double translateX = yCoord * TILE_SIZE;


        setTranslateX(translateX);
        setTranslateY(translateY);
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public String getText() {
        return text.getText();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isMined() {
        return isMined;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean hasQuestion() {
        return hasQuestion;
    }

    //Процедура открытия ячейки
    public void setOpen() {
        isOpen = true;
        border.setFill(null);
        if (!isMined) text.setVisible(true);
        else mine.setVisible(true);
        if (hasQuestion) setQuestion(false);
    }

    public void setMined() {
        isMined = true;
    }

    public void flag(boolean flagged) {
        isFlagged = flagged;
        flag.setVisible(flagged);
    }

    public void setQuestion(boolean hsQuestion) {
        hasQuestion = hsQuestion;
        question.setVisible(hsQuestion);
    }

    public void setText(String string) {
        this.text.setText(string);
    }

    public void setGroup(MineField field, ArrayList<BotGroup> groups) {
        if (field.isFirstClick() || !this.isOpen() || this.getText().isEmpty())
            return;
        BotGroup newGroup = new BotGroup(Integer.parseInt(this.getText()));
        List<Tile> neighbours = field.getNeighbours(this);
        for (Tile neighbour : neighbours) {
            if (!neighbour.isOpen() && !this.isFlagged()) {
                newGroup.add(neighbour);
            }
        }
        groups.add(newGroup);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;
        Tile obj = (Tile) o;
        return (this.getyCoord() == obj.getyCoord()) && (this.getxCoord() == obj.getxCoord());
    }
}
