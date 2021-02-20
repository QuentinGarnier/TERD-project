package World;

public class Room {
    public static final int MIN_WIDTH = 3;
    public static final int MIN_HEIGHT = 3;
    private final Position topLeft;
    private final Position bottomRight;
    private boolean heroIsHere = false; // if true room is shown

    public Room(Cell[][] lab) throws NO_MORE_SPACE_ERROR {
        this.topLeft = findTopLeft(lab);
        this.bottomRight = findBottomRight(lab);
        putRandomEltInRoom(lab);
    }
    Position findTopLeft(Cell[][] lab) throws NO_MORE_SPACE_ERROR{
        /* TODO: 20/02/21
            if there is no more space left throw NO_MORE_SPACE_ERROR
         */
        return null;
    }
    Position findBottomRight(Cell[][] lab){
        // TODO: 20/02/21  bottom right is computed from w et topLeft
        return null;
    }
    void putRandomEltInRoom(Cell[][] lab){

    }
}
