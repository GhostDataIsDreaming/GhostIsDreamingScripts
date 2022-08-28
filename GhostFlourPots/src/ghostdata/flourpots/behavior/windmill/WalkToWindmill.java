package ghostdata.flourpots.behavior.windmill;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.framework.behaviortree.premade.TilePathWalker;
import ghostdata.framework.utils.CameraUtils;

public class WalkToWindmill extends TilePathWalker {

    public WalkToWindmill() {
        super(
                ScriptStats.WIMDMILL_LOCATION.getWindmillArea(),
                ScriptStats.WIMDMILL_LOCATION.getPathways());
    }

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.WALKING_TO_WINDMILL;
    }

    @Override
    public Object onArrival() {
        ScriptStats.CURRENT_STEP = ScriptStep.ADDING_TO_HOPPER;

        CameraUtils.randomZoom();
        return -1;
    }
}
