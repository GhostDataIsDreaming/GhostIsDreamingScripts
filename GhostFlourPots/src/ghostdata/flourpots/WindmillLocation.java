package ghostdata.flourpots;

import ghostdata.flourpots.vars.GrainLocation;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;

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
    ),
    COOKS_GUILD(
            "Cooks' Guild",
            GrainLocation.COOKS_GUILD,
//            ScriptStats.R.nextBoolean() ? BankLocation.VARROCK_WEST : BankLocation.GRAND_EXCHANGE,
            BankLocation.COOKS_GUILD,
            new Area(
                    new Tile(3140, 3453, 0), new Tile(3138, 3451, 0), new Tile(3138, 3448, 0), new Tile(3142, 3444, 0),
                    new Tile(3144, 3444, 0), new Tile(3146, 3446, 0), new Tile(3148, 3450, 0), new Tile(3148, 3452, 0),
                    new Tile(3146, 3453, 0)
            ),
            new Tile(3142, 3152, 2),
            new Tile(3141, 4153, 2),
            new Tile(3140, 3559)) {
            @Override
            public boolean hasRequirements() {
                try {
                    return Skills.getRealLevel(Skill.COOKING) >= 32;
                } catch (Exception e) {
                    return false;
                }
            }
        }
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

    public boolean hasRequirements() {
        return true;
    }
}
