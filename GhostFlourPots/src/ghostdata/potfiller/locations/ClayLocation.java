package ghostdata.potfiller.locations;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public enum ClayLocation {

    SOUTH_WEST_VARROCK(
            "South West Varrock Mine",
            new Tile(3182, 3379, 0),
            new Tile(3171, 3366, 0),
            new Tile(3185, 3366, 0),
            new Tile(3185, 3380, 0)
    ),
    DWARVEN_MINE("Dwarven Mine",  // TODO - Refine for better player placement, This just encompasses entire mine
            new Tile(2985, 9850, 0),
            new Tile(2986, 9698, 0),
            new Tile(3083, 9696, 0),
            new Tile(3063, 9861, 0)
    ),
    CRAFTING_GUILD(
            "Crafting Guild Mine",
            new Tile(2939, 3291, 0),
            new Tile(2942, 3291, 0),
            new Tile(2943, 3277, 0),
            new Tile(2939, 3276, 0),
            new Tile(2937, 3281, 0)
    ),
    CAMDOZAAL_MINES("Camdozaal Mines"),
    RIMMINGTON_MINE("Rimmington Mine")
    ;

    public final String name;
    public final Area area;

    ClayLocation(String name, Area area) {
        this.name = name;
        this.area = area;
    }

    ClayLocation(String name, Tile... tiles) {
        this.name = name;
        this.area = new Area(tiles);
    }
}
