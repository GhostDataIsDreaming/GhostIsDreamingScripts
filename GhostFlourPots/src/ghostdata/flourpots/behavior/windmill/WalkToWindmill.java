package ghostdata.flourpots.behavior.windmill;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.framework.behaviortree.premade.WalkTo;
import org.dreambot.api.methods.map.Area;

public class WalkToWindmill extends WalkTo {

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.WALKING_TO_WINDMILL;
    }

    @Override
    public Area getArea() {
        return ScriptStats.WIMDMILL_LOCATION.getWindmillArea();
    }

    @Override
    public Object onArrive() {
        ScriptStats.CURRENT_STEP = ScriptStep.ADDING_TO_HOPPER;
        return -1;
    }
}
