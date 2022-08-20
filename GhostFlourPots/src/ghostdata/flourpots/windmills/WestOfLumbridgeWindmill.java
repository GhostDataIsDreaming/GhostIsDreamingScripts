package ghostdata.flourpots.windmills;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public class WestOfLumbridgeWindmill extends WindmillLocation {

    private static final Area WINDMILL = new Area(
            new Tile(3165, 3312, 0),
            new Tile(3161, 3307, 0),
            new Tile(3161, 3305, 0),
            new Tile(3165, 3302, 0),
            new Tile(3168, 3302, 0),
            new Tile(3171, 3305, 0),
            new Tile(3171, 3308, 0),
            new Tile(3168, 3312, 0));

    private static final Tile FLOUR_BIN_TILE = new Tile(3166, 3306);

    public WestOfLumbridgeWindmill() {
        this(BankLocation.DRAYNOR);
    }

    public WestOfLumbridgeWindmill(BankLocation bankLocation) {
        super("West of Lumbridge", WINDMILL);

        this.bankingLocation = bankLocation;
        this.hopperTile = new Tile(3166, 3307, 2);
        this.hopperControlTile = new Tile(3166, 3305, 2);
        this.flourBinTile = FLOUR_BIN_TILE;
    }
}
