package Labyrinthe;

public enum Move {
    //Axe abscisse orienté à droite
    //Axe ordonnée orienté en haut
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGTH(1, 0);

    public int x, y;

    Move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getMoveX(){
        return x;
    }

    public int getMoveY() {
        return y;
    }
}
