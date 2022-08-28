package ghostdata.flourpots.behavior.materials.gains;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.WindmillLocation;
import ghostdata.framework.behaviortree.premade.WalkTo;
import org.dreambot.api.methods.map.Area;

public class WalkToGrains extends WalkTo {

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.WALKING_TO_GRAINS;
    }

    @Override
    public Area getArea() {
        return ScriptStats.WIMDMILL_LOCATION.getGrainLocation().area;
    }

    @Override
    public Object onArrive() {
        ScriptStats.CURRENT_STEP = ScriptStep.PICK_GRAINS;
        return -1;
    }
}
