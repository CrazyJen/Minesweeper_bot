import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Settings {
    private static int X_TILES = 9;
    private static int Y_TILES = 9;
    private static int MINES = 20;

    public void openSettings() {
        Stage settingsWindow = new Stage();
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.setTitle("Settings");

        Text widthText = new Text("Tiles in width (5 - 20):");
        TextField widthTextField = new TextField(String.valueOf(Y_TILES));
        HBox widthHBox = new HBox(widthText, widthTextField);
        widthHBox.setSpacing(5);
        widthHBox.setAlignment(Pos.CENTER);

        Text heightText = new Text("Tiles in height (5 - 15):");
        TextField heightTextField = new TextField(String.valueOf(X_TILES));
        HBox heightHBox = new HBox(heightText, heightTextField);
        heightHBox.setSpacing(5.0);
        heightHBox.setAlignment(Pos.CENTER);

        Text minesText = new Text("Amount of mines (10 - 0,4*x*y):");
        TextField minesTextField = new TextField(String.valueOf(MINES));
        HBox minesHBox = new HBox(minesText, minesTextField);
        minesHBox.setSpacing(5.0);
        minesHBox.setAlignment(Pos.CENTER);

        Button okButton = new Button("OK"),
                cancelButton = new Button("Отмена");
        cancelButton.setOnAction(e -> settingsWindow.close());
        okButton.setOnAction(e -> {
            if (Integer.parseInt(heightTextField.getText()) <= 15 && Integer.parseInt(heightTextField.getText()) >= 5 &&
                    Integer.parseInt(widthTextField.getText()) <= 20 &&
                    Integer.parseInt(widthTextField.getText()) >= 5 &&
                    Integer.parseInt(minesTextField.getText()) <= 0.4 * Integer.parseInt(heightTextField.getText()) *
                            Integer.parseInt(widthTextField.getText()) &&
                    Integer.parseInt(minesTextField.getText()) >= 10) {
                X_TILES = Integer.parseInt(heightTextField.getText());
                Y_TILES = Integer.parseInt(widthTextField.getText());
                MINES = Integer.parseInt(minesTextField.getText());
                settingsWindow.close();
            } else {
                ResultWindow.openResultWindow("Wrong parameters", "Wrong input settings");
            }
        });
        HBox buttons = new HBox(okButton, cancelButton);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(widthHBox, heightHBox, minesHBox, buttons);
        vBox.setSpacing(5);
        BorderPane settings = new BorderPane();
        settings.setCenter(vBox);
        settings.setTop(new HBox(new Text("")));

        Scene settingsScene = new Scene(settings, 330, 150);
        settingsWindow.setScene(settingsScene);
        settingsWindow.show();
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
}
