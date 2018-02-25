package Rules;

public class VerticalRules {
    public static boolean checkKey (String value) {
        return value.matches("^[a-zA-Z]+|[а-яА-Я]+$");
    }
    public static boolean check (String key, String string) {
        return key.length() > 1 && key.length() < string.length();
    }
}
