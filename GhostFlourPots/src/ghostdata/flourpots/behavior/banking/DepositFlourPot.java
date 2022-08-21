package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.behavior.BankingNodeParent;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.utilities.impl.Condition;

public class DepositFlourPot implements Node {

//    @Override
//    public boolean isValid() {
//        return (Bank.isOpen() && Inventory.getEmptySlots() < 28)
//                && Inventory.count(FlourPotItems.POT.id) != 14
//                && Inventory.count(FlourPotItems.GRAIN.id) != 14;
//                && !Inventory.contains(FlourPotItems.POT.id)
//                && !Inventory.contains(FlourPotItems.GRAIN.id);
//    }

    @Override
    public Object tick() {
        Bank.depositAllItems(); //TODO - Swap between hotkey, deposit all, deposit individually
        return (Condition) () -> Inventory.isEmpty();
    }

    @Override
    public boolean isValid() {
        if (Bank.isOpen()) {
            if (Inventory.isFull() || Inventory.getEmptySlots() <= 28) {
                return true;
            }

            if (Inventory.contains(FlourPotItems.POT_OF_FLOUR.id)) {
                return true;
            }

            if (Inventory.contains(FlourPotItems.POT.id) && Inventory.contains(FlourPotItems.GRAIN.id)) {
                return false;
            }

            if ((Inventory.contains(FlourPotItems.POT.id) && !Inventory.contains(FlourPotItems.GRAIN.id)) || (Inventory.contains(FlourPotItems.GRAIN.id) && !Inventory.contains(FlourPotItems.POT.id))) {
                GhostFlourPots.currentStep = ScriptStep.NO_REQUIREMENTS;
                return true;
            }
        }

        return false;
    }
}
