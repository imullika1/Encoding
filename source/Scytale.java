import java.util.ArrayList;
import java.util.List;

class Scytale {
    // Variables
    private Integer key;
    private String input;
    private StringBuilder output = new StringBuilder();
    private List<List<Character>> rows = new ArrayList<>();

    // Constructor
    Scytale(String input, Integer key) {
        this.key = key;
        this.input = input;
        this.init();
    }

    // Setup rows of input text
    private void init () {
        for (String string : input.split("(?<=\\G.{" + key + "})")) {
            List<Character> charArray = new ArrayList<>();
            for (int i = 0; i < string.length(); i++) charArray.add(string.charAt(i));
            rows.add(new ArrayList<>(charArray));
        }
    }

    // Encoding
    String encode () {
        for (int i = 0; i < rows.get(0).size(); i++) {
            for (List row: rows) {
                try { output.append(row.get(i)); }
                catch (IndexOutOfBoundsException ignored) { output.append("*"); }
            }
        }
        return  String.valueOf(output);
    }

    // Decoding
    String decode () {
        String[] secondArrayOfStrings = input.split("(?<=\\G.{" + rows.size() + "})");
        for (int i = 0; i < secondArrayOfStrings[0].length(); i++) {
            for (String string: secondArrayOfStrings) {
                try { output.append(string.charAt(i)); }
                catch (StringIndexOutOfBoundsException ignored) { }
            }
        }
        return  String.valueOf(output.toString().replace("*", ""));
    }

}
