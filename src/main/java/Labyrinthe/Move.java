package Labyrinthe;

public enum Move {
    //Axe X oriented to the right
    //Axe Y oriented to de top
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0);

    private Position move;

    Move(int x, int y){
        move = new Position(x, y);
    }

    public Position getMove(){
        return move;
    }
}
