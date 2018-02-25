import Methods.DoubleReplacement;
import Methods.Scytale;
import Methods.VerticalReplacement;
import Rules.DoubleRules;
import Rules.ScytaleRules;
import Rules.VerticalRules;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    // UI Elements
    public Button start;
    public Label error;
    public Label counter;
    public TextArea input;
    public TextArea output;
    public TextField key_1;
    public TextField key_2;
    public ChoiceBox<String> action;
    public ChoiceBox<String> algorithm;

    // Setup on startup
    public void initialize() {

        // Setup Action ChoiceBox
        action.setItems(FXCollections.observableArrayList(
            "Зашифровать",
            "Расшивровать"
        ));
        action.getSelectionModel().select(0);

        // Setup Algorithm ChoiceBox
        algorithm.setItems(FXCollections.observableArrayList(
            "Скитала",
            "Шифровальные таблицы",
            "Двойная перестановка"
        ));
        algorithm.getSelectionModel().select(0);

        // Input text length counter
        input
            .textProperty()
            .addListener((observable, oldValue, newValue) ->
                    counter.setText("Количество символов: " + input.getLength())
            );

        // Algorithm select event
        algorithm
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue.equals(2)) key_2.setVisible(true);
                        else key_2.setVisible(false);
                        key_1.setText("");
                        key_2.setText("");
                    }
                );

        // Input key event
        key_1.textProperty().addListener((observable, oldValue, newValue) -> checkKeyRules(oldValue, newValue));
        key_2.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (!DoubleRules.checkKey(newValue)) {
                    key_2.setText(oldValue);
                    error.setText(
                        "Для метода шифрования двойной перестановки " +
                        "требуется указать ключ одним " +
                        "десятичным числом без пробелов и символов."
                    );
                } else key_2.setText(newValue);
            }
        );

        // Setup Start Button
        start.setOnMouseClicked(event -> checkRules());
    }

    // Check key input
    private void checkKeyRules(String oldValue, String newValue) {
        if (newValue.length() != 0) {

            // Null error message
            error.setText("");
            // Set start button visible
            start.setVisible(true);
            // Set algorithm variable
            Integer algorithm = this.algorithm.getSelectionModel().getSelectedIndex();

            // Check scytale key input rules
            if (algorithm == 0)
                // Scytale key
                if (!ScytaleRules.checkKey(newValue)) {
                    key_1.setText(oldValue);
                    error.setText(
                        "Для метода шифрования скитала " +
                        "требуется указать ключ одним " +
                        "десятичным числом без пробелов и символов."
                    );
                } else key_1.setText(newValue);
            else if (algorithm == 1)
                // Vertical Replacement key
                if (!VerticalRules.checkKey(newValue)) {
                    key_1.setText(oldValue);
                    error.setText(
                        "Для метода шифрования шифровальных таблиц " +
                        "требуется указать ключ, состоящий " +
                        "из слова или набора символов из одного афавита " +
                        ", без знаков, цифр и пробелов."
                    );
                } else key_1.setText(newValue);
            else
                // Double Replacement keys
                if (!DoubleRules.checkKey(newValue)) {
                    key_1.setText(oldValue);
                    error.setText(
                        "Для метода шифрования двойной перестановки " +
                        "требуется указать ключ одним " +
                        "десятичным числом без пробелов и символов."
                    );
                } else key_1.setText(newValue);
        } else start.setVisible(false);
    }

    private void checkRules() {
        Integer action = this.action.getSelectionModel().getSelectedIndex();
        Integer algorithm = this.algorithm.getSelectionModel().getSelectedIndex();

        if (input.getLength() != 0) {
            if (algorithm == 0) {
                // Scytale
                if (ScytaleRules.check(key_1.getText(), input.getText())) {
                    Scytale scytale = new Scytale(input.getText(), Integer.parseInt(key_1.getText()));
                    if (action == 0) output.setText(scytale.encode());
                    else output.setText(scytale.decode());
                } else
                    error.setText(
                        "Ключ должен быть больше 1.\n" +
                        "Не должен быть равен длине текста.\n" +
                        "Не должен быть больше длины текста."
                    );
            } else if (algorithm == 1) {
                // Vertical Replacement
                if (VerticalRules.check(key_1.getText(), input.getText())) {
                    VerticalReplacement vertical = new VerticalReplacement(input.getText(), key_1.getText());
                    if (action == 0) output.setText(vertical.encode());
                    else output.setText(vertical.decode());
                } else
                    error.setText(
                        "Ключ должен состоять как минимум из\n" +
                        "2 символов и быть меньше длины текста."
                    );
            } else {
                // Double Replacement
                if (DoubleRules.check(key_1.getText(), key_2.getText(), input.getText())) {
                    DoubleReplacement doubleReplacement = new DoubleReplacement(
                        input.getText(),
                        key_1.getText(),
                        key_2.getText()
                    );
                    if (action == 0) output.setText(doubleReplacement.encode());
                    else output.setText(doubleReplacement.decode());
                } else
                    error.setText(
                        "Ключ должен быть больше 1.\n" +
                        "Не должен быть равен длине текста.\n" +
                        "Не должен быть больше длины текста.\n"
                    );
            }
        } else error.setText("Введите текст для шифровки/расшифровки");
    }
}
