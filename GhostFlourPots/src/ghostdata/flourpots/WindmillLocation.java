package ghostdata.flourpots;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public enum WindmillLocation {
    WEST_OF_LUMBRIDGE(
            "West of Lumbridge",
            BankLocation.DRAYNOR,
            new Area(
                    new Tile(3165, 3312, 0),
                    new Tile(3161, 3307, 0),
                    new Tile(3161, 3305, 0),
                    new Tile(3165, 3302, 0),
                    new Tile(3168, 3302, 0),
                    new Tile(3171, 3305, 0),
                    new Tile(3171, 3308, 0),
                    new Tile(3168, 3312, 0)),
            new Tile(3166, 3307, 2),
            new Tile(3166, 3305, 2),
            new Tile(3166, 3306)
    )
    ;

    private final String name;
    private final BankLocation bankLocation;

    private final Area windmillArea;

    private final Tile hopperTile;
    private final Tile hopperControlsTile;
    private final Tile flourBinTile;

    WindmillLocation(String name, BankLocation bankLocation, Area windmillArea, Tile hopperTile, Tile hopperControlsTile, Tile flourBinTile) {
        this.name = name;
        this.bankLocation = bankLocation;
        this.windmillArea = windmillArea;
        this.hopperTile = hopperTile;
        this.hopperControlsTile = hopperControlsTile;
        this.flourBinTile = flourBinTile;
    }

    public String getName() {
        return name;
    }

    public BankLocation getBankLocation() {
        return bankLocation;
    }

    public Area getWindmillArea() {
        return windmillArea;
    }

    public Tile getHopperTile() {
        return hopperTile;
    }

    public Tile getHopperControlsTile() {
        return hopperControlsTile;
    }

    public Tile getFlourBinTile() {
        return flourBinTile;
    }
}
