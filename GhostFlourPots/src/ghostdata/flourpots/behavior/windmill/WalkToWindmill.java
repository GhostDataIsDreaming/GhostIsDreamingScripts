package ghostdata.flourpots.behavior.windmill;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.ScriptStep;
import ghostdata.framework.behaviortree.premade.WalkTo;
import ghostdata.framework.behaviortree.premade.WalkToArea;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;

public class WalkToWindmill extends WalkTo {

    @Override
    public boolean isValid() {
        return GhostFlourPots.currentStep == ScriptStep.WALKING_TO_WINDMILL;
    }

    @Override
    public Area getArea() {
        return GhostFlourPots.selectedWindmillLocation.getWindmillArea();
    }

    @Override
    public Object onArrive() {
        GhostFlourPots.currentStep = ScriptStep.ADDING_TO_HOPPER;
        return -1;
    }
}
