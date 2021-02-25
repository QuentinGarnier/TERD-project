package graphics;

public class ColorStr {
    public static final String DEFAULT = "\033[0m";
    public static String black(String txt) { return "\033[30m" + txt + DEFAULT; }
    public static String red(String txt) { return "\033[31m" + txt + DEFAULT; }
    public static String green(String txt) { return "\033[32m" + txt + DEFAULT; }
    public static String yellow(String txt) { return "\033[33m" + txt + DEFAULT; }
    public static String blue(String txt) { return "\033[34m" + txt + DEFAULT; }
    public static String magenta(String txt) { return "\033[35m" + txt + DEFAULT; }
    public static String cyan(String txt) { return "\033[36m" + txt + DEFAULT; }
    public static String white(String txt) { return "\033[37m" + txt + DEFAULT; }
}
