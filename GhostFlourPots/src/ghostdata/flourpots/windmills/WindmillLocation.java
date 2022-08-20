package ghostdata.flourpots.windmills;

import ghostdata.flourpots.ScriptStep;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public abstract class WindmillLocation {

    private final String name;
    BankLocation bankingLocation;

    private final Area windmillArea;
    Tile hopperTile;
    Tile hopperControlTile;
    Tile flourBinTile;

    public WindmillLocation(String name, Area windmillArea) {
        this.name = name;
        this.windmillArea = windmillArea;
    }

    public WindmillLocation(String name, Area windmillArea, BankLocation bankingLocation, Tile hopperTile, Tile hopperControlTile, Tile flourBinTile) {
        this(name, windmillArea);
        this.bankingLocation = bankingLocation;
        this.hopperTile = hopperTile;
        this.hopperControlTile = hopperControlTile;
        this.flourBinTile = flourBinTile;
    }

    public String getName() {
        return name;
    }

    public BankLocation getBankingLocation() {
        return bankingLocation;
    }

    public Area getWindmillArea() {
        return windmillArea;
    }

    public Tile getHopperTile() {
        return hopperTile;
    }

    public Tile getHopperControlTile() {
        return hopperControlTile;
    }

    public Tile getFlourBinTile() {
        return flourBinTile;
    }
}
