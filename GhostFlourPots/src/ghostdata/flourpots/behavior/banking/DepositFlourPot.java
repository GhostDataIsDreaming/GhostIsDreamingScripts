package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.utilities.impl.Condition;

public class DepositFlourPot implements Node {

    @Override
    public Object tick() {
        Bank.depositAllItems(); //TODO - Swap between hotkey, deposit all, deposit individually
        return (Condition) () -> Inventory.isEmpty();
    }

    @Override
    public boolean isValid() {
        if (Bank.isOpen()) {
            if (Inventory.isEmpty()) {
                return false;
            }

            if (Inventory.contains(FlourPotItems.POT_OF_FLOUR.id)) {
                return true;
            }

            if (Inventory.contains(FlourPotItems.POT.id) && Inventory.contains(FlourPotItems.GRAIN.id)) {
                return false;
            }

            if ((Inventory.contains(FlourPotItems.POT.id) && !Inventory.contains(FlourPotItems.GRAIN.id)) || (Inventory.contains(FlourPotItems.GRAIN.id) && !Inventory.contains(FlourPotItems.POT.id))) {
                ScriptStats.CURRENT_STEP = ScriptStep.NO_REQUIREMENTS;
                return true;
            }

            if (Inventory.isFull() || Inventory.getEmptySlots() < 28) {
                return true;
            }
        }

        return false;
    }
}
