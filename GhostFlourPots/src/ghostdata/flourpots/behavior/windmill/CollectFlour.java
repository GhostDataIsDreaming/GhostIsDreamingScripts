package ghostdata.flourpots.behavior.windmill;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class CollectFlour implements Node {

    private static final String INTERACT = "Fill";

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.COLLECTING_FLOUR;
    }

    @Override
    public Object tick() {
        if (Inventory.count(FlourPotItems.POT.id) == 0) {
            ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_BANK;
            return -1;
        }

        Tile binTile = ScriptStats.WIMDMILL_LOCATION.getFlourBinTile();
        GameObject flourBin = GameObjects.closest(obj -> obj.getTile().equals(binTile));

        if (flourBin != null) {
            if (Calculations.random(0, 100) <= 10) {
                flourBin.interactForceRight(INTERACT);
            } else {
                flourBin.interact();
            }

//            EntityUtils.interactEntity(flourBin, INTERACT);
            return Calculations.random(5, 500);
        } else {
            Walking.walk(binTile.getRandomizedTile(2));
            return (Condition) () -> Walking.getDestinationDistance() <= 2;
        }
    }
}
