package ghostdata.flourpots;

import ghostdata.flourpots.vars.GrainLocation;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public enum WindmillLocation {
    WEST_OF_LUMBRIDGE(
            "West of Lumbridge",
            GrainLocation.DRAYNOR,
            BankLocation.DRAYNOR,
            new Area(
                    new Tile(3165, 3312, 0), new Tile(3161, 3307, 0), new Tile(3161, 3305, 0), new Tile(3165, 3302, 0),
                    new Tile(3168, 3302, 0), new Tile(3171, 3305, 0), new Tile(3171, 3308, 0), new Tile(3168, 3312, 0)),
            new Tile[][] {
                    new Tile[] {
                            new Tile(3092, 3248, 0), new Tile(3104, 3249, 0), new Tile(3107, 3264, 0),
                            new Tile(3134, 3262, 0), new Tile(3161, 3264, 0), new Tile(3166, 3287, 0), new Tile(3166, 3305, 0)
                    },
                    new Tile[]  {
                            new Tile(3093, 3248, 0), new Tile(3103, 3250, 0), new Tile(3110, 3262, 0),
                            new Tile(3135, 3262, 0), new Tile(3135, 3293, 0), new Tile(3155, 3293, 0), new Tile(3166, 3285, 0),
                            new Tile(3166, 3305, 0)
                    },
                    new Tile[] {
                            new Tile(3093, 3248, 0), new Tile(3104, 3249, 0), new Tile(3109, 3261, 0),
                            new Tile(3104, 3273, 0), new Tile(3110, 3294, 0), new Tile(3146, 3294, 0), new Tile(3163, 3287, 0),
                            new Tile(3167, 3286, 0), new Tile(3166, 3305, 0)
                    },
                    new Tile[] {
                            new Tile(3092, 3248, 0), new Tile(3104, 3249, 0), new Tile(3105, 3274, 0),
                            new Tile(3110, 3294, 0), new Tile(3136, 3305, 0), new Tile(3157, 3309, 0), new Tile(3166, 3299, 0),
                            new Tile(3166, 3305, 0)
                        }
            },
            new Tile(3166, 3307, 2),
            new Tile(3166, 3305, 2),
            new Tile(3166, 3306)
    )
    ;

    private final String name;
    private final GrainLocation grainLocation;

    private final BankLocation bankLocation;

    private final Area windmillArea;
    private final Tile[][] pathways;
    private final Tile hopperTile;
    private final Tile hopperControlsTile;
    private final Tile flourBinTile;

    WindmillLocation(String name, GrainLocation grainLocation, BankLocation bankLocation, Area windmillArea, Tile hopperTile, Tile hopperControlsTile, Tile flourBinTile) {
        this.name = name;
        this.grainLocation = grainLocation;
        this.bankLocation = bankLocation;
        this.windmillArea = windmillArea;
        this.pathways = null;
        this.hopperTile = hopperTile;
        this.hopperControlsTile = hopperControlsTile;
        this.flourBinTile = flourBinTile;
    }

    WindmillLocation(String name, GrainLocation grainLocation, BankLocation bankLocation, Area windmillArea, Tile[][] pathways, Tile hopperTile, Tile hopperControlsTile, Tile flourBinTile) {
        this.name = name;
        this.grainLocation = grainLocation;
        this.bankLocation = bankLocation;
        this.windmillArea = windmillArea;
        this.pathways = pathways;
        this.hopperTile = hopperTile;
        this.hopperControlsTile = hopperControlsTile;
        this.flourBinTile = flourBinTile;
    }

    public String getName() {
        return name;
    }

    public GrainLocation getGrainLocation() {
        return grainLocation;
    }

    public BankLocation getBankLocation() {
        return bankLocation;
    }

    public Area getWindmillArea() {
        return windmillArea;
    }

    public Tile[][] getPathways() {
        return pathways;
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
