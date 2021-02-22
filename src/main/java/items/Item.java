package items;

public abstract class Item {
    private final int id;
    private final String name;
    private final ItemType type;

    Item(int i, String n, ItemType t) {
        this.id = i;
        this.name = n;
        this.type = t;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ItemType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
