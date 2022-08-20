package ghostdata.flourpots.behavior;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;

public class ResumeNode implements Node {

    public boolean completed = false;

    @Override
    public boolean isValid() {
        return GhostFlourPots.currentStep == ScriptStep.RESUME && !completed;
    }

    @Override
    public Object tick() {
        if (Inventory.contains(FlourPotItems.POT_OF_FLOUR.id) || Inventory.isEmpty()) {
            GhostFlourPots.currentStep = ScriptStep.BANKING;
        } else if (GhostFlourPots.selectedWindmillLocation.getWindmillArea().contains(Players.localPlayer().getTile())) {
            if (Inventory.contains(FlourPotItems.GRAIN.id)) {
                GhostFlourPots.currentStep = ScriptStep.ADDING_TO_HOPPER;
            } else if (Inventory.contains(FlourPotItems.POT.id)) {
                GhostFlourPots.currentStep = ScriptStep.COLLECTING_FLOUR;
            } else {
                GhostFlourPots.currentStep = ScriptStep.BANKING;
            }
        } else {
            if (Inventory.count(FlourPotItems.POT.id) == 14 && Inventory.count(FlourPotItems.GRAIN.id) == 14) {
                GhostFlourPots.currentStep = ScriptStep.WALKING_TO_WINDMILL;
            } else {
                GhostFlourPots.currentStep = ScriptStep.BANKING;
            }
        }

        completed = true;

        return 0;
    }
}
