import graphics.ColorStr;
import graphics.map.WorldMap;

public class Main {
    public static void main(String[] args) {
        System.out.println(ColorStr.RED + "HELLO \033[0m");

        System.out.println(WorldMap.getInstanceWorld());
    }
}
