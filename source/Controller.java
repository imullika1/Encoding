import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Button startButton;
    public TextField keyInput;
    public TextArea textInput;
    public TextArea textOutput;
    public TextArea errorOutput;
    public ChoiceBox<String> actionSelect;
    public ChoiceBox<String> algorithmSelect;
    public TextField keyInput2;

    // Setup choice boxes
    private ObservableList<String> actionItems = FXCollections.observableArrayList("Зашифровать", "Расшивровать");
    private ObservableList<String> algorithmItems = FXCollections.observableArrayList("Скитала", "Вертикальная перестановка", "Двойная перестановка");

    // Setup on startup
    public void initialize() {
        // Setup Action ChoiceBox
        actionSelect.setItems(actionItems);
        actionSelect.setValue(actionItems.get(0));

        // Setup Algorithm ChoiceBox
        algorithmSelect.setItems(algorithmItems);
        algorithmSelect.setValue(algorithmItems.get(0));

        // Setup Start Button
        startButton.setOnMouseClicked(event -> onStartButtonClicked());
    }

    // Start button click event
    private void onStartButtonClicked() {
        // Check what type of action chosen: encoding/decoding
        if (actionSelect.getSelectionModel().getSelectedIndex() == 0) {
            // If encoding - check the method of encoding
            if (algorithmSelect.getSelectionModel().getSelectedIndex() == 0) encodeUsingScytale();
            else if (algorithmSelect.getSelectionModel().getSelectedIndex() == 1) encodeUsingVerticalReplacement();
            else if (algorithmSelect.getSelectionModel().getSelectedIndex() == 2) encodeUsingDoubleReplacement();
        } else {
            // If decoding - check the method of decoding
            if (algorithmSelect.getSelectionModel().getSelectedIndex() == 0) decodeUsingScytale();
            else if (algorithmSelect.getSelectionModel().getSelectedIndex() == 1) decodeUsingVerticalReplacement();
            else if (algorithmSelect.getSelectionModel().getSelectedIndex() == 2) decodeUsingDoubleReplacement();
        }
    }

    // Double Encoding & Decoding
    private void encodeUsingDoubleReplacement () {

        // Init horizontal key array
        List<Integer> horizontalKeyArray = new ArrayList<>();
        // Init vertical key array
        List<Integer> verticalKeyArray = new ArrayList<>();

        // Reformat horizontal key input to string array
        String[] horizontalKeyStringArray = keyInput.getText().replaceAll("\\s", "").split(",");
        // Reformat vertical key input to string array
        String[] verticalKeyStringArray = keyInput2.getText().replaceAll("\\s", "").split(",");

        // Filling up horizontalKeyArray
        for (String horizontalStringyInteger: horizontalKeyStringArray) horizontalKeyArray.add(Integer.parseInt(horizontalStringyInteger));
        // Filling up verticalKeyArray
        for (String verticalStringyInteger: verticalKeyStringArray) verticalKeyArray.add(Integer.parseInt(verticalStringyInteger));

        // Setup horizontal key length
        Integer horizontalKeyLength = horizontalKeyArray.size();
        // Setup vertical key length
        Integer verticalKeyLength = verticalKeyArray.size();

        // Compare for input text and key length
        if (horizontalKeyLength > textInput.getLength()) errorOutput.setText("Ключ не может быть длиннее текста.");
        // Setup decoded double dimensional array
        else {
            // Init counter
            Integer counter = 0;

            // Init string builder for format encoded output string
            StringBuilder encodedString = new StringBuilder();

            // Init single dimensional array
            List<Character> colCharacterSingleDimensionalArray = new ArrayList<>();

            // Init double dimensional array
            List<List<Character>> rowCharacterDoubleDimensionalArray = new ArrayList<>();

            // Format single & double dimensional arrays
            for (int i = 0; i < textInput.getLength(); i++) {
                colCharacterSingleDimensionalArray.add(textInput.getText().charAt(i));
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
                for (List list: rowCharacterDoubleDimensionalArray) {
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
                for (List list: rowVerticalEncodedDoubleDimensionalArray) {
                    encodedString.append(list.get(verticalKeyArray.indexOf(counter)));
                }
                counter++;
            }

            textOutput.setText(encodedString.toString());
        }
    }
    private void decodeUsingDoubleReplacement () { }

    // Vertical Encoding & Decoding
    private void encodeUsingVerticalReplacement() {
        // Init integer array
        List<Integer> integerKeyArray = new ArrayList<>();

        // Reformat key input to string array
        String[] reformatedStringArray = keyInput.getText().replaceAll("\\s", "").split(",");

        // Reformat string array to integer array
        for (String stringInteger : reformatedStringArray) integerKeyArray.add(Integer.parseInt(stringInteger));

        // Setup the key length
        Integer keyLength = integerKeyArray.size();

        // Check for input text and key length
        if (keyLength > textInput.getText().length()) errorOutput.setText("Ключ не может быть длиннее текста.");
        // Format decoded double dimensional array
        else {
            // Init counter
            Integer counter = 0;

            // Init string builder for format encoded output string
            StringBuilder encodedString = new StringBuilder();

            // Setup single dimensional array
            List<Character> colCharacterSingleDimensionalArray = new ArrayList<>();

            // Setup double dimensional array
            List<List<Character>> rowCharacterDoubleDimensionalArray = new ArrayList<>();

            // Format single & double dimensional arrays
            for (int i = 0; i < textInput.getLength(); i++) {
                colCharacterSingleDimensionalArray.add(textInput.getText().charAt(i));
                if (counter == keyLength - 1) {
                    rowCharacterDoubleDimensionalArray.add(new ArrayList<>(colCharacterSingleDimensionalArray));
                    colCharacterSingleDimensionalArray.clear();
                    counter = 0;
                } else counter++;
            }

            // Prepare counter for new loop
            counter = 1;

            // Format encoded string for output
            for (int index = 0; index < keyLength; index++) {
                for (List list : rowCharacterDoubleDimensionalArray) {
                    encodedString.append(list.get(integerKeyArray.indexOf(counter)));
                }
                counter++;
            }

            // Output encoded string
            textOutput.setText(encodedString.toString());
        }
    }
    private void decodeUsingVerticalReplacement() { }

    // Scytale Encoding & Decoding
    private void encodeUsingScytale() {
        // Setup key
        Integer key = Integer.parseInt(keyInput.getText());

        // Check key & input string length
        if (textInput.getLength() < key) errorOutput.setText("Ключ не может быть длинее текста");
        else {
            // Init counter
            Integer counter = 1;

            // Init string builder for format encoded output string
            StringBuilder encodedString = new StringBuilder();

            // Setup single dimensional array of cols
            List<Character> colCharacterSingleDimensionalArray = new ArrayList<>();

            // Setup double dimensional array of rows
            List<List<Character>> rowCharacterDoubleDimensionalArray = new ArrayList<>();

            // Format decoded double dimensional array
            for (int i = 0; i < textInput.getLength(); i++) {
                colCharacterSingleDimensionalArray.add(textInput.getText().charAt(i));
                if (!counter.equals(key)) counter++;
                else {
                    rowCharacterDoubleDimensionalArray.add(new ArrayList<>(colCharacterSingleDimensionalArray));
                    colCharacterSingleDimensionalArray.clear();
                    counter = 1;
                }
            }

            // Format encoded string for output
            for (int i = 0; i < rowCharacterDoubleDimensionalArray.get(0).size(); i++) {
                for (List row: rowCharacterDoubleDimensionalArray) {
                    encodedString.append(row.get(i));
                }
            }

            // Output encoded string
            textOutput.setText(encodedString.toString());
        }

    }
    private void decodeUsingScytale() { }
}
