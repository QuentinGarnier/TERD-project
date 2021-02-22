package graphics.elements;

public enum Move {
    //X axis oriented to the right
    //Y axis oriented to the top
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0);

    private Position move;

    Move(int x, int y){
        move = new Position(x, y);
    }

    public Position getMove(){
        return move;
    }
}
