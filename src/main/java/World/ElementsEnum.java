package World;

public enum ElementsEnum {
    WALL('+'), MONSTER('O'), OUTSIDE_ROOM('?'),
    TREE('&'), HERO('@'), ITEM('%'), EMPTY(' ');

    private char symbol;

    ElementsEnum(char c){ symbol = c;}

    public char getSymbol(){
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
