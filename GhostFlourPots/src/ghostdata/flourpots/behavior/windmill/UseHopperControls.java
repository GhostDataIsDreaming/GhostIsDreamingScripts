package ghostdata.flourpots.behavior.windmill;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

public class UseHopperControls implements Node {

    private static final int ANIMATION = 3571;
    private static final String INTERACT = "Operate";

    @Override
    public boolean isValid() {
        return GhostFlourPots.currentStep == ScriptStep.USE_CONTROLS;
    }

    @Override
    public Object tick() {
        Tile controlTile = GhostFlourPots.selectedWindmillLocation.getHopperControlsTile();
        GameObject control = GameObjects.closest(obj -> obj.getTile().equals(controlTile));

        if (control != null) {
            if (Calculations.random(0, 100) <= 25) {
                control.interactForceRight(INTERACT);
            } else {
                control.interact();
            }

            GhostFlourPots.currentStep = ScriptStep.USE_CONTROLS;

            if (Inventory.count(FlourPotItems.GRAIN.id) == 0) {
                GhostFlourPots.currentStep = ScriptStep.COLLECTING_FLOUR;
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
