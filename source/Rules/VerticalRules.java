package Rules;

public class VerticalRules {
    public static boolean checkKey (String newValue, String oldValue) {
        if (newValue.matches("^[a-zA-Z]+|[а-яА-Я]+$")) {
            boolean result = false;
            for (Character character: oldValue.toCharArray())
                result = character.equals(newValue.charAt(newValue.length() - 1));
            return result;
        }
        else return false;
    }
    public static boolean check (String key, String string) {
        return key.length() > 1 && key.length() < string.length();
    }
}
