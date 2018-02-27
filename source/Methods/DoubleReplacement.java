package Methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoubleReplacement {
    private List<Integer> key_1 = new ArrayList<>();
    private List<Integer> key_1Sorted = new ArrayList<>();
    private List<Integer> key_2 = new ArrayList<>();
    private List<Integer> key_2Sorted = new ArrayList<>();
    private String input;
    private List<Character> cols;
    private List<Object> colsNew;
    private List<List<Character>> rows = new ArrayList<>();
    private List<List<Character>> rowsNew = new ArrayList<>();
    private StringBuilder output = new StringBuilder();

    public DoubleReplacement (String input, String key_1, String key_2) {
        this.input = input;

        String[] stringifyKey_1 = key_1.split("");
        String[] stringifyKey_2 = key_2.split("");

        for (String aStringifyKey_1 : stringifyKey_1)
            this.key_1.add(Integer.parseInt(aStringifyKey_1));

        for (String aStringifyKey_2 : stringifyKey_2)
            this.key_2.add(Integer.parseInt(aStringifyKey_2));

        for (String substring: input.split("(?<=\\G.{" + this.key_1.size() + "})")) {
            cols = new ArrayList<>();

            for (int i = 0; i < this.key_1.size(); i++)
                try { cols.add(substring.charAt(i)); }
                catch (StringIndexOutOfBoundsException ignored) { cols.add('*'); }

            rows.add(new ArrayList<>(cols));
        }

        key_1Sorted.addAll(this.key_1);
        key_2Sorted.addAll(this.key_2);

        Collections.sort(key_1Sorted);
        Collections.sort(key_2Sorted);
    }

    public String encode () {

        for (int i = 0; i < rows.size(); i++)
            rowsNew.add(rows.get(key_2.indexOf(key_2Sorted.get(i))));

        rows.clear();

        for (int i = 0; i < rowsNew.get(0).size(); i++) {
            colsNew = new ArrayList<>();

            for (List row : rowsNew)
                colsNew.add(row.get(i));

            rows.add(new ArrayList(colsNew));
        }

        for (List row : rows)
            for (Object character : row)
                output.append((Object) character);

        return String.valueOf(output);
    }

    public String decode () {



        return "";
    }
}
