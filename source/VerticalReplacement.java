import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerticalReplacement {
    // Variables
    private String key;
    private String input;
    private StringBuilder output = new StringBuilder();
    private List<Character> unsortedKey = new ArrayList<>();
    private List<Character> sortedKey = new ArrayList<>();
    private List<List<Character>> rows = new ArrayList<>();

    // Constructor
    VerticalReplacement (String input, String key) {
        this.key = key;
        this.input = input;
        init();
    }

    private void init () {
        for (int i = 0; i < key.length(); i++)
            unsortedKey.add(key.charAt(i));

        sortedKey = new ArrayList<>(unsortedKey);
        Collections.sort(sortedKey);

        for (String string : input.split("(?<=\\G.{" + sortedKey.size() + "})")) {
            List<Character> charArray = new ArrayList<>();

            for (int i = 0; i < string.length(); i++)
                charArray.add(string.charAt(i));

            rows.add(new ArrayList<>(charArray));
        }
    }

    String encode () {
        for (int i = 0; i < rows.get(0).size(); i++) {
            for (List row: rows) {
                try { output.append(row.get(unsortedKey.indexOf(sortedKey.get(i)))); }
                catch (IndexOutOfBoundsException ignored) { output.append("*"); }
            }
        }
        return  String.valueOf(output);
    }

    String decode () {
        List<Character> charCols = new ArrayList<>();
        List<List<Character>> charRows = new ArrayList<>();

        for (String substring: input.split("(?<=\\G.{" + rows.size() + "})")) {
            for (Character theChar : substring.toCharArray())
                charCols.add(theChar);

            charRows.add(new ArrayList<>(charCols));
            charCols.clear();
        }

        StringBuilder createdString = new StringBuilder();
        for (int i = 0; i < charRows.get(0).size(); i++)
            for (List<Character> charRow : charRows)
                createdString.append(charRow.get(i));

        rows.clear();

        for (String string : createdString.toString().split("(?<=\\G.{" + sortedKey.size() + "})")) {
            List<Character> charArray = new ArrayList<>();

            for (int i = 0; i < string.length(); i++)
                charArray.add(string.charAt(i));

            rows.add(new ArrayList<>(charArray));
        }

        charRows.clear();
        for (List ignored : rows)
            charRows.add(new ArrayList<>());

        for (int i = 0; i < rows.get(0).size(); i++) {
            for (int j = 0; j < rows.size(); j++) {
                try { charRows.get(j).add(rows.get(j).get(sortedKey.indexOf(unsortedKey.get(i)))); }
                catch (IndexOutOfBoundsException ignored) { output.append("*"); }
            }
        }

        for (List row: charRows)
            for (Object theChar: row)
                output.append(theChar);

        return  String.valueOf(output.toString().replace("*", ""));
    }
}
