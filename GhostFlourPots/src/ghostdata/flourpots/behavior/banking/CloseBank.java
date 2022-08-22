package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.utilities.impl.Condition;

public class CloseBank implements Node {

    @Override
    public boolean isValid() {
        return Bank.isOpen()
//                && Inventory.isFull()
                && !Inventory.contains(FlourPotItems.POT_OF_FLOUR)
                && Inventory.contains(FlourPotItems.POT.id)
                && Inventory.contains(FlourPotItems.GRAIN.id);
    }

    @Override
    public Object tick() {
        Bank.close(); //TODO - Swap between using hotkeys, pressing close or just walking away.
        ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_WINDMILL;
        return (Condition) () -> !Bank.isOpen();
    }
}
