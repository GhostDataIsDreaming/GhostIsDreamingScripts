package ghostdata.flourpots.behavior;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;

public class ResumeNode implements Node {

    public boolean completed = false;

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.RESUME && !completed;
    }

    @Override
    public Object tick() {
        if (Inventory.contains(FlourPotItems.POT_OF_FLOUR.id) || Inventory.isEmpty()) {
            ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_BANK;
        } else if (ScriptStats.WIMDMILL_LOCATION.getWindmillArea().contains(Players.localPlayer().getTile())) {
            if (Inventory.contains(FlourPotItems.GRAIN.id)) {
                ScriptStats.CURRENT_STEP = ScriptStep.ADDING_TO_HOPPER;
            } else if (Inventory.contains(FlourPotItems.POT.id)) {
                ScriptStats.CURRENT_STEP = ScriptStep.COLLECTING_FLOUR;
            } else {
                ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_BANK;
            }
        } else {
            if (Inventory.contains(FlourPotItems.POT.id, FlourPotItems.GRAIN.id)) {
                ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_WINDMILL;
            } else {
                ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_BANK;
            }
        }

        completed = true;

        return 0;
    }
}
