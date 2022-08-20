package ghostdata.flourpots.vars;

import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.items.Item;

public enum FlourPotItems {

    GRAIN(1947, "Grain"),
    POT(1931, "Pot"),
    POT_OF_FLOUR(1933, "Pot of flour");

    public int id;
    public String name;

    public Filter<Item> itemFilter;

    FlourPotItems(int id, String name) {
        this.name = name;
        this.id = id;

        this.itemFilter = item -> item.getID() == id || item.getName().equalsIgnoreCase(name);
    }

    FlourPotItems(int id, String name, Filter<Item> itemFilter) {
        this.id = id;
        this.name = name;
        this.itemFilter = itemFilter;
    }
}
