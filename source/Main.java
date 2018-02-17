import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;

import java.io.IOException;

public class Main extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    public void start (Stage stage) throws IOException {
        stage.setTitle("Приложение шифрования");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("View.fxml")), 600, 400));
        stage.show();
    }
}
