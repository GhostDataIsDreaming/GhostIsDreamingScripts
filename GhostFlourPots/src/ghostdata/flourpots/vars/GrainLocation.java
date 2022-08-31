package ghostdata.flourpots.vars;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public enum GrainLocation {
    DRAYNOR( //Area closest to the bank
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
            new Tile(3129, 3275, 0)
    ),
    WEST_OF_LUMBRIDGE( //Unlucky Area, But closer to windmill
            new Tile(3153, 3305, 0),
            new Tile(3153, 3297, 0),
            new Tile(3161, 3291, 0),
            new Tile(3163, 3293, 0),
            new Tile(3165, 3299, 0),
            new Tile(3156, 3308, 0)
    ),
    COOKS_GUILD(new Area(3139, 3463, 3143, 3458))
    ;

    public final Area area;

    GrainLocation(Area area) {
        this.area = area;
    }

    GrainLocation(Tile... tiles) {
        this.area = new Area(tiles);
    }
}
