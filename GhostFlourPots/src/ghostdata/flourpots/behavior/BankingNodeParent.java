package ghostdata.flourpots.behavior;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.behavior.banking.*;
import ghostdata.framework.behaviortree.ParentNode;
import org.dreambot.api.methods.container.impl.bank.Bank;

public class BankingNodeParent extends ParentNode {

    public BankingNodeParent() {
        super(
                new WalkingToBank(),
                new BankingNode());
    }

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.BANKING || ScriptStats.CURRENT_STEP == ScriptStep.WALKING_TO_BANK;
    }
}
