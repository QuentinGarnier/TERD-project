import graphics.ColorStr;
import graphics.map.WorldMap;

public class Main {
    public static void main(String[] args) {
        System.out.println(ColorStr.RED + "HELLO" + ColorStr.WHITE + " WORLD" + ColorStr.DEFAULT + " TEST");
        System.out.println(WorldMap.getInstanceWorld());
    }
}
