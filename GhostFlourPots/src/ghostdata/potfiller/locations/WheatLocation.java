package ghostdata.potfiller.locations;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public enum WheatLocation {
    WEST_OF_LUMBRIDGE(
            "West of Lumbridge",
            new Tile(3153, 3305, 0),
            new Tile(3153, 3297, 0),
            new Tile(3161, 3291, 0),
            new Tile(3163, 3293, 0),
            new Tile(3165, 3299, 0),
            new Tile(3156, 3308, 0)),
    DRAYNOR_VILLAGE(
            "Draynor Village",
            new Tile(3129, 3268, 0),
            new Tile(3110, 3268, 0),
            new Tile(3107, 3271, 0),
            new Tile(3107, 3277, 0),
            new Tile(3112, 3282, 0),
            new Tile(3112, 3288, 0),
            new Tile(3117, 3292, 0),
            new Tile(3129, 3291, 0),
            new Tile(3131, 3284, 0),
            new Tile(3131, 3277, 0),
            new Tile(3129, 3275, 0)),
    COOKS_GUILD(
            "Cooks' Guild",
            new Area(3139, 3463, 3143, 3458))
    ;

    public final String name;
    public final Area area;

    WheatLocation(String name, Area area) {
        this.name = name;
        this.area = area;
    }

    WheatLocation(String name, Tile... tiles) {
        this.name = name;
        this.area = new Area(tiles);
    }
}
