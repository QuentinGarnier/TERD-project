package graphics.window;

import graphics.elements.Corridor;
import graphics.elements.Position;
import graphics.elements.Room;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FogLabel extends JLabel {
    public static final int size = GamePanel.size;
    private final Corridor c;
    private final Room r;
    private final List<Position> doors;
    public FogLabel(Corridor c, Room r, int x, int y){
        super(new ImageIcon("data/images/map/grounds/fog.png"));
        this.doors = new ArrayList<>();
        this.c = c; this.r = r;
        setBounds(x * size, y * size, size, size);
    }

    public boolean updatePosition(){
        if (c != null){
            if (c.isHasBeenVisited()){
                setLocation(-size, -size);
                doors.addAll(c.getDoorList());
                return true;
            }
        } else if (r != null) {
            if (r.isHasBeenVisited()) {
                setLocation(-size, -size);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FogLabel){
            return ((FogLabel) o).getLocation().equals(getLocation());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(c, r);
    }
}
