package ghostdata.flourpots.behavior;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.behavior.banking.WalkingToBank;
import ghostdata.flourpots.behavior.materials.gains.PickGrains;
import ghostdata.flourpots.behavior.materials.gains.WalkToGrains;
import ghostdata.framework.behaviortree.ParentNode;

public class MaterialsParentNode extends ParentNode {

    public MaterialsParentNode(int grainsToPick) {
        super(
                new WalkToGrains(),
                new PickGrains(grainsToPick)
        );
    }

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.WALKING_TO_GRAINS || ScriptStats.CURRENT_STEP == ScriptStep.PICK_GRAINS;
    }
}
