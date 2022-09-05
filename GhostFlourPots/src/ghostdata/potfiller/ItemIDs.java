package ghostdata.potfiller;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.LivePrices;

public enum ItemIDs {
    GRAIN(1947, "Grain"),
    POT(1931, "Pot"),
    POT_OF_FLOUR(1933, "Pot of flour"),
    POT_OF_CORNFLOUR("Pot of cornflour"),
    CLAY("Clay"),
    SOFT_CLAY("Soft clay"),
    UNFIRED_POT("Unfired pot"),
    SWEETCORN("Sweetcorn"),
    CORNFLOUR("Cornflour"),
    CHEFS_HAT(1949, "Chef's hat") //Needed for Cooks Guild
    ;

    public final int id;
    public final String name;

    boolean updatedCost = false;
    private int grandExchangePrice = -1;

    ItemIDs(String name) {
        this(-1, name);
    }

    ItemIDs(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int countInventory() {
        return Inventory.count(id);
    }

    public boolean hasInInventory() {
        return Inventory.contains(id);
    }

    public boolean hasInBank() {
        return Bank.contains(id);
    }

    public int countBank() {
        return Bank.count(id);
    }

    public int getGrandExchangePrice() {
        if (grandExchangePrice == -1 || !updatedCost) {
            grandExchangePrice = LivePrices.get(id);
            updatedCost = true;
        }

        return grandExchangePrice;
    }

    public static ItemIDs find(String name) {
        for (ItemIDs id : values()) {
            if (id.name.equalsIgnoreCase(name)) {
                return id;
            }
        }

        return null;
    }
}
