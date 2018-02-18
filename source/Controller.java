import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public TextArea textInput;
    public TextArea textOutput;
    public ChoiceBox<String> actionSelect;
    public ChoiceBox<String> algorithmSelect;
    public Button startButton;
    public TextField keyInput;
    public TextArea errorOutput;

    private ObservableList<String> actionItems = FXCollections.observableArrayList("Зашифровать", "Расшивровать");
    private ObservableList<String> algorithmItems = FXCollections.observableArrayList("Скитала", "Вертикальная перестановка");

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

    private void onStartButtonClicked() {
        if (actionSelect.getSelectionModel().getSelectedIndex() == 0) {
            if (algorithmSelect.getSelectionModel().getSelectedIndex() == 0) encodeUsingScytale();
            else if (algorithmSelect.getSelectionModel().getSelectedIndex() == 1) encodeUsingVerticalReplacement();
        } else {
            if (algorithmSelect.getSelectionModel().getSelectedIndex() == 0) decodeUsingScytale();
            else if (algorithmSelect.getSelectionModel().getSelectedIndex() == 1) decodeUsingVerticalReplacement();
        }
    }

    // Vertical Encoding & Decoding
    private void encodeUsingVerticalReplacement() {
        // Init integer array
        List<Integer> integerKeyArray = new ArrayList<>();

        // Setup double dimensional array
        List<List<Character>> characterColsDoubleDimensionalArray = new ArrayList<>();

        // Setup single dimensional array
        List<Character> characterRowsSingleDimensionalArray = new ArrayList<>();

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
            Integer counter = 0;
            // Format single & double dimensional arrays
            for (int i = 0; i < textInput.getLength(); i++) {
                characterRowsSingleDimensionalArray.add(textInput.getText().charAt(i));
                if (counter == keyLength - 1) {
                    characterColsDoubleDimensionalArray.add(new ArrayList<>(characterRowsSingleDimensionalArray));
                    characterRowsSingleDimensionalArray.clear();
                    counter = 0;
                } else counter++;
            }
        }

        // Init key counter
        int counter = 1;
        // Encode and format encoded output
        StringBuilder encodedString = new StringBuilder();
        for (int index = 0; index < keyLength; index++) {
            for (List list : characterColsDoubleDimensionalArray) {
                encodedString.append(list.get(integerKeyArray.indexOf(counter)));
            }
            counter++;
        }
        // Output encoded string
        textOutput.setText(encodedString.toString());
    }
    private void decodeUsingVerticalReplacement() { }

    // Scytale Encoding & Decoding
    private void encodeUsingScytale() {
        // Calculate num of rows for char array
        double calculatedRows = Math.ceil((double) textInput.getText().length() / Integer.parseInt(keyInput.getText()));
        // Init double char array
        char decodedArray[][] = new char[(int) calculatedRows][Integer.parseInt(keyInput.getText())];
        // Counters for loop
        int col = 0;
        int row = 0;
        // Make char array
        for (int i = 0; i < textInput.getText().length(); i++) {
            decodedArray[row][col] = textInput.getText().charAt(i);
            if (col == Integer.parseInt(keyInput.getText()) - 1) {
                col = 0;
                row++;
            }
            else col++;
        }
        // Encode Build output string
        StringBuilder encoded = new StringBuilder();
        for (int cols = 0; cols < decodedArray[0].length; cols++) {
            for (char[] DecodedArray : decodedArray) {
                encoded.append(DecodedArray[cols]);
            }
        }

        textOutput.setText(encoded.toString());
    }
    private void decodeUsingScytale() {
        // Init string to decode
        String encodedString = textInput.getText();
        // Init string builder for format new sting
        StringBuilder decodedStringBuilder = new StringBuilder();
        // Calculate num of rows for char array
        double calculatedRows = Math.ceil((double) textInput.getText().length() / Integer.parseInt(keyInput.getText()));
        // Init double char array
        char decodedArray[][] = new char[(int) calculatedRows][Integer.parseInt(keyInput.getText())];
        // Counters for loop
        int col = 0;
        int row = 0;
        // Decode loop
        for (int times = 0; times < Integer.parseInt(keyInput.getText()); times++) {
            col = 0;
            row = 0;
            for (int i = 0; i < encodedString.length(); i++) {
                decodedArray[row][col] = encodedString.charAt(i);
                if (col == Integer.parseInt(keyInput.getText()) - 1) {
                    col = 0;
                    row++;
                }
                else col++;
            }

            for (int cols = 0; cols < decodedArray[0].length; cols++) {
                for (char[] DecodedArray : decodedArray) {
                    decodedStringBuilder.append(DecodedArray[cols]);
                }
            }

            encodedString = decodedStringBuilder.toString();
            decodedStringBuilder = new StringBuilder();
            System.out.println(encodedString);
        }

        textOutput.setText(encodedString);
    }
}
