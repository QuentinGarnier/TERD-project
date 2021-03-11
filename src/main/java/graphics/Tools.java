package graphics;

import java.awt.im.InputContext;
import java.util.Locale;

public class Tools {

    public static String getKeyboard() {
        InputContext context = InputContext.getInstance();
        Locale country = context.getLocale();
        return country.toString();
    }

    public static char universalCharOf(char key) {
        return switch (key) {
            case 'z' -> 'w';
            case 'q' -> 'a';
            case 'a' -> 'q';
            case 'w' -> 'z';
            default -> key;
        };
    }

    public static void showCommands() {
        char top = 'w', left = 'a', attack = 'q';
        if (getKeyboard().equals("fr_FR")){ top = 'z'; left = 'q'; attack = 'a';}
        System.out.printf(System.lineSeparator() + "To move : %c (top), %c (left), s (bottom), d (right)%sTo attack (not effective): %c%sTo leave : p%s", top, left, System.lineSeparator(), attack, System.lineSeparator(), System.lineSeparator());
    }

    public static class TextEffects {

        public static final String DEFAULT = "\033[0m";
        public static String black(String txt) { return "\033[30m" + txt + DEFAULT; }
        public static String red(String txt) { return "\033[31m" + txt + DEFAULT; }
        public static String green(String txt) { return "\033[32m" + txt + DEFAULT; }
        public static String yellow(String txt) { return "\033[33m" + txt + DEFAULT; }
        public static String blue(String txt) { return "\033[34m" + txt + DEFAULT; }
        public static String magenta(String txt) { return "\033[35m" + txt + DEFAULT; }
        public static String cyan(String txt) { return "\033[36m" + txt + DEFAULT; }
        public static String white(String txt) { return "\033[37m" + txt + DEFAULT; }

        public static String blackBG(String txt) { return "\033[40m" + txt + DEFAULT; }
        public static String redBG(String txt) { return "\033[41m" + txt + DEFAULT; }
        public static String greenBG(String txt) { return "\033[42m" + txt + DEFAULT; }
        public static String yellowBG(String txt) { return "\033[43m" + txt + DEFAULT; }
        public static String blueBG(String txt) { return "\033[44m" + txt + DEFAULT; }
        public static String magentaBG(String txt) { return "\033[45m" + txt + DEFAULT; }
        public static String cyanBG(String txt) { return "\033[46m" + txt + DEFAULT; }
        public static String whiteBG(String txt) { return "\033[47m" + txt + DEFAULT; }

        public static String encircled(String txt) { return "\033[52m" + txt + DEFAULT; }
    }
}
