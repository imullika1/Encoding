package Methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleReplacement {
    private int[] key_1;
    private int[] key_1Sorted;
    private int[] key_2;
    private int[] key_2Sorted;
    private String input;
    private List<Character> cols;
    private List<Character> colsNew;
    private List<List<Character>> rows = new ArrayList<>();
    private List<List<Character>> rowsNew = new ArrayList<>();
    private StringBuilder output;

    public DoubleReplacement (String input, String key_1, String key_2) {
        this.input = input;

        this.key_1 = new int[key_1.length()];
        this.key_2 = new int[key_2.length()];

        String[] stringifyKey_1 = key_1.split("");
        String[] stringifyKey_2 = key_2.split("");

        for (int i = 0; i < stringifyKey_1.length; i++)
            this.key_1[i] = Integer.parseInt(stringifyKey_1[i]);

        for (int i = 0; i < stringifyKey_2.length; i++)
            this.key_2[i] = Integer.parseInt(stringifyKey_2[i]);
    }

    public String encode () {
        for (String substring: input.split("(?<=\\G.{" + key_1.length + "})")) {
            cols = new ArrayList<>();

            for (int i = 0; i < substring.length(); i++)
                cols.add(substring.charAt(i));

            rows.add(new ArrayList<>(cols));
        }

        key_1Sorted = new int[key_1.length];
        key_2Sorted = new int[key_2.length];

        System.arraycopy(key_1, 0, key_1Sorted, 0, key_1.length);
        System.arraycopy(key_2, 0, key_2Sorted, 0, key_2.length);

        Arrays.sort(key_1Sorted);
        Arrays.sort(key_2Sorted);

        for (int i = 0; i < rows.size(); i++)
//            rowsNew.add(new ArrayList<>(rows.get()));
//TODO
        System.out.println(rowsNew);

        return "";
    }

    public String decode () {

        return "";
    }
}
