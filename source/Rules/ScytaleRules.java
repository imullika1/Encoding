package Rules;

public class ScytaleRules {
    public static boolean checkKey (String value) {
        return value.matches("^[0-9]+$");
    }
    public static boolean check (String key, String string) {
        return Integer.parseInt(key) > 1 && Integer.parseInt(key) < string.length();
    }
}
