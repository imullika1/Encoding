package Rules;

public class DoubleRules {

    public static boolean checkKey (String value) {
        return value.matches("^[0-9]+$");
    }
    public static boolean check (String key_1, String key_2, String string) {
        return
                key_1.length() > 1
                && key_1.length() < string.length()
                && (int) Math.ceil((double) string.length()/key_1.length()) == key_2.length();
    }
}
