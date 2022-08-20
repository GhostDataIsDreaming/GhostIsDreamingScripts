package ghostdata.flourpots.behavior;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.behavior.windmill.AddToHopper;
import ghostdata.flourpots.behavior.windmill.CollectFlour;
import ghostdata.flourpots.behavior.windmill.UseHopperControls;
import ghostdata.flourpots.behavior.windmill.WalkToWindmill;
import ghostdata.framework.behaviortree.ParentNode;

public class WindmillNodeParent extends ParentNode {

    public WindmillNodeParent() {
        super(
                new WalkToWindmill(),
                new AddToHopper(),
                new UseHopperControls(),
                new CollectFlour());
    }

    @Override
    public boolean isValid() {
        return GhostFlourPots.currentStep != ScriptStep.BANKING;
    }
}
