import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResultWindow {

    public static void openResultWindow(String title, String message) {
        Stage failWindow = new Stage();
        failWindow.initModality(Modality.APPLICATION_MODAL);
        failWindow.setTitle(title);

        Text text = new Text(message);
        text.setFont(Font.font(18));

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> failWindow.close());


        VBox vBox = new VBox(text, okButton);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        BorderPane settings = new BorderPane();
        settings.setCenter(vBox);
        settings.setTop(new HBox(new Text("")));

        Scene settingsScene = new Scene(settings, 200, 100);
        failWindow.setScene(settingsScene);
        failWindow.show();
    }
}
