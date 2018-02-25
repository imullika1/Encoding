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
        action.setItems(FXCollections.observableArrayList("Зашифровать", "Расшивровать"));
        action.getSelectionModel().select(0);

        // Setup Algorithm ChoiceBox
        algorithm.setItems(FXCollections.observableArrayList(
                "Скитала", "Шифровальные таблицы", "Двойная перестановка"
        ));
        algorithm.getSelectionModel().select(0);

        // Input text length counter
        input
                .textProperty()
                .addListener(
                        (observable, oldValue, newValue) -> counter.setText("Количество символов: " + input.getLength())
                );

        // Algorithm select event
        algorithm
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(2)) key_2.setVisible(true);
                    else key_2.setVisible(false);
                    key_1.setText("");
                });

        // Input key rules
        key_1.textProperty().addListener((observable, oldValue, newValue) -> checkKeyRules(oldValue, newValue));

        // Setup Start Button
        start.setOnMouseClicked(event -> checkRules());
    }

    // Check key input rules
    private void checkKeyRules(String oldValue, String newValue) {
        if (newValue.length() != 0) {

            // Null error message
            error.setText("");

            // Set start button visible
            start.setVisible(true);

            // Set algorithm variable
            Integer algorithm = this.algorithm.getSelectionModel().getSelectedIndex();

            // Check scytale key input rules
            if (algorithm == 0) {
                if (!newValue.chars().allMatch(element -> newValue.matches("^[0-9]+$"))) {
                    key_1.setText(oldValue);
                    error.setText(
                        "Для метода шифрования скитала " +
                        "требуется указать ключ одним " +
                        "десятичным числом без пробелов и символов."
                    );
                } else key_1.setText(newValue);
            } else if (algorithm == 1) {
                if (!newValue.chars().allMatch(element -> newValue.matches("^[a-zA-Z]+|[а-яА-Я]+$"))) {
                    key_1.setText(oldValue);
                    error.setText(
                        "Для метода шифрования шифровальных таблиц " +
                        "требуется указать ключ, состоящий " +
                        "из слова или набора символов из одного афавита " +
                        ", без знаков, цифр и пробелов."
                    );
                } else key_1.setText(newValue);
            }
        } else start.setVisible(false);

    }

    private void checkRules() {
        String key_1 = this.key_1.getText();
        String input = this.input.getText();
        Integer action = this.action.getSelectionModel().getSelectedIndex();
        Integer algorithm = this.algorithm.getSelectionModel().getSelectedIndex();

        if (input.length() != 0) {
            if (algorithm == 0) {
                if (Integer.parseInt(key_1) != 0 && Integer.parseInt(key_1) != 1)
                    if (input.length() > Integer.parseInt(key_1)) {
                        Scytale scytale = new Scytale(input, Integer.parseInt(key_1));
                        if (action == 0) output.setText(scytale.encode());
                        else output.setText(scytale.decode());
                    } else error.setText("Использование длины ключа больше или равной длине текста бесполезно.");
                else error.setText("Ключ не должен быть равен 0 или 1.");
            } else if (algorithm == 1) {
                if (key_1.length() != 0 && key_1.length() != 1) {
                    if (input.length() > key_1.length()) {
                        VerticalReplacement vertical = new VerticalReplacement(input, key_1);
                        if (action == 0) output.setText(vertical.encode());
                        else output.setText(vertical.decode());
                    } else error.setText("Использование длины ключа больше или равной длине текста бесполезно");
                } else error.setText("Ключ должен состоять как минимум из 2 символов.");
            }
        } else error.setText("Введите текст для шифровки/расшифровки");
    }

    // Double Encoding & Decoding
    private void encodeUsingDoubleReplacement() {

        // Init horizontal key array
        List<Integer> horizontalKeyArray = new ArrayList<>();
        // Init vertical key array
        List<Integer> verticalKeyArray = new ArrayList<>();

        // Reformat horizontal key input to string array
        String[] horizontalKeyStringArray = key_1.getText().replaceAll("\\s", "").split(",");
        // Reformat vertical key input to string array
        String[] verticalKeyStringArray = key_2.getText().replaceAll("\\s", "").split(",");

        // Filling up horizontalKeyArray
        for (String horizontalStringyInteger : horizontalKeyStringArray)
            horizontalKeyArray.add(Integer.parseInt(horizontalStringyInteger));
        // Filling up verticalKeyArray
        for (String verticalStringyInteger : verticalKeyStringArray)
            verticalKeyArray.add(Integer.parseInt(verticalStringyInteger));

        // Setup horizontal key length
        Integer horizontalKeyLength = horizontalKeyArray.size();
        // Setup vertical key length
        Integer verticalKeyLength = verticalKeyArray.size();

        // Compare for input text and key length

        // Setup decoded double dimensional array

        // Init counter
        Integer counter = 0;

        // Init string builder for format encoded output string
        StringBuilder encodedString = new StringBuilder();

        // Init single dimensional array
        List<Character> colCharacterSingleDimensionalArray = new ArrayList<>();

        // Init double dimensional array
        List<List<Character>> rowCharacterDoubleDimensionalArray = new ArrayList<>();

        // Format single & double dimensional arrays
        for (int i = 0; i < input.getLength(); i++) {
            colCharacterSingleDimensionalArray.add(input.getText().charAt(i));
            if (counter != horizontalKeyLength - 1) counter++;
            else {
                rowCharacterDoubleDimensionalArray.add(new ArrayList<>(colCharacterSingleDimensionalArray));
                colCharacterSingleDimensionalArray.clear();
                counter = 0;
            }
        }
        // Prepare counter for vertical encoding
        counter = 1;

        // Init vertical encoded single dimensional array
        List<Object> colVerticalEncodedSingleDimensionalArray = new ArrayList<>();
        // Init vertical encoded double dimensional array
        List<List<Object>> rowVerticalEncodedDoubleDimensionalArray = new ArrayList<>();

        // Format vertical encoding
        for (int i = 0; i < horizontalKeyLength; i++) {
            for (List list : rowCharacterDoubleDimensionalArray) {
                colVerticalEncodedSingleDimensionalArray.add(list.get(horizontalKeyArray.indexOf(counter)));
            }
            rowVerticalEncodedDoubleDimensionalArray.add(new ArrayList<>(colVerticalEncodedSingleDimensionalArray));
            colVerticalEncodedSingleDimensionalArray.clear();
            counter++;
        }

        // Prepare counter for horizontal encoding
        counter = 1;

        // Format horizontal encoding
        for (int i = 0; i < verticalKeyLength; i++) {
            for (List list : rowVerticalEncodedDoubleDimensionalArray) {
                encodedString.append(list.get(verticalKeyArray.indexOf(counter)));
            }
            counter++;
        }

        output.setText(encodedString.toString());
    }
}
