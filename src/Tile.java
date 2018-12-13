import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private int xCoord, yCoord;
    private boolean isOpen = false;
    private boolean isMined = false;
    private boolean isFlagged = false;
    private boolean hasQuestion = false;
    private Circle border;
    private Text text = new Text();
    private ImageView flag = new ImageView(new Image("flag.png"));
    private ImageView mine = new ImageView(new Image("mine.png"));
    private ImageView question = new ImageView(new Image("question.png"));


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

    public void setFlagged(boolean flagged) {
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
}
