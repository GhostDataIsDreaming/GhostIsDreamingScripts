package ghostdata.flourpots.behavior.windmill;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.utils.EntityUtils;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class AddToHopper implements Node {

    private static final int ANIMATION = 3572;
    private static final String INTERACT = "Fill";

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.ADDING_TO_HOPPER;
    }

    @Override
    public Object tick() {
        if (Inventory.count(FlourPotItems.GRAIN.id) == 0 && Inventory.contains(FlourPotItems.POT.id)) {
            ScriptStats.CURRENT_STEP = ScriptStep.COLLECTING_FLOUR;
            return -1;
        }

        Tile hopperTile = ScriptStats.WIMDMILL_LOCATION.getHopperTile();
        GameObject hopper = GameObjects.closest(obj -> obj.getTile().equals(hopperTile));

        if (hopper != null) {
//            if (Calculations.random(0, 100) <= 25) {
//                hopper.interactForceRight(INTERACT);
//            } else {
//                hopper.interact();
//            }

            EntityUtils.interactEntity(hopper, INTERACT);

            ScriptStats.CURRENT_STEP = ScriptStep.USE_CONTROLS;
            return new Object[] {
                    (Condition) () -> Players.localPlayer().getAnimation() == ANIMATION,
                    3000
            };
        } else {
            Walking.walk(hopperTile.getRandomizedTile(2));
            return (Condition) () -> Walking.getDestinationDistance() <= 2;
        }
    }
}
