import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class Controller {
    public TextArea textInput;
    public TextArea textOutput;
    public ChoiceBox<String> actionSelect;
    public ChoiceBox algoritmSelect;
    public Button startButton;

    private ObservableList<String> actionItems = FXCollections.observableArrayList("Зашифровать", "Расшивровать");

    public void initialize() {
        // Setup Action ChoiceBox
        actionSelect.setItems(actionItems);
        actionSelect.setValue(actionItems.get(0));

        // Setup Start Button
        startButton.setOnMouseClicked(event -> onStartButtonClicked());
    }

    private void onStartButtonClicked() {

    }
}
