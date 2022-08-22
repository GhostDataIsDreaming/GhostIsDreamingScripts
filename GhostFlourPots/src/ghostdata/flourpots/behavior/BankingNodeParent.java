package ghostdata.flourpots.behavior;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.behavior.banking.*;
import ghostdata.framework.behaviortree.ParentNode;

public class BankingNodeParent extends ParentNode {

    public BankingNodeParent() {
        super(
                new WalkingToBank(),
                new OpenBank(),
                new DepositFlourPot(),
                new WithdrawRequiredItems(),
                new CloseBank());
    }

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.BANKING;
    }
}
