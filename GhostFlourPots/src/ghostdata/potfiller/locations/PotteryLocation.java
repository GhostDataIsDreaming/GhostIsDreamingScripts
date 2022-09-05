package ghostdata.potfiller.locations;

public enum PotteryLocation {

    // Free
    BARBARIAN_VILLAGE("Barbarian Village"),
    CRAFTING_GUILD("Crafting Guild")

    // Members
    ;

    public final String name;

    PotteryLocation(String name) {
        this.name = name;
    }

    public boolean hasRequirement() {
        return true;
    }
}
