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

public class UseHopperControls implements Node {

    private static final int ANIMATION = 3571;
    private static final String INTERACT = "Operate";

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.USE_CONTROLS;
    }

    @Override
    public Object tick() {
        Tile controlTile = ScriptStats.WIMDMILL_LOCATION.getHopperControlsTile();
        GameObject control = GameObjects.closest(obj -> obj.getTile().equals(controlTile));

        if (control != null) {
//            if (Calculations.random(0, 100) <= 25) {
//                control.interactForceRight(INTERACT);
//            } else {
//                control.interact();
//            }

            EntityUtils.interactEntity(control, INTERACT);
            ScriptStats.CURRENT_STEP = ScriptStep.USE_CONTROLS;

            if (Inventory.count(FlourPotItems.GRAIN.id) == 0) {
                ScriptStats.CURRENT_STEP = ScriptStep.COLLECTING_FLOUR;
            }

            return new Object[] {
                (Condition) () -> Players.localPlayer().getAnimation() == ANIMATION,
                3000
            };
        } else {
            Walking.walk(controlTile.getRandomizedTile(2));
            return (Condition) () -> Walking.getDestinationDistance() <= 2;
        }
    }
}
