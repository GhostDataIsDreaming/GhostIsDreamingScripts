package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.behavior.BankingNodeParent;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.utilities.impl.Condition;

public class DepositFlourPot implements Node {

    @Override
    public boolean isValid() {
        return (Bank.isOpen() && Inventory.getEmptySlots() < 28)
                && Inventory.count(FlourPotItems.POT.id) != 14
                && Inventory.count(FlourPotItems.GRAIN.id) != 14;
    }

    @Override
    public Object tick() {
        Bank.depositAllItems(); //TODO - Swap between hotkey, deposit all, deposit individually
        return (Condition) () -> Inventory.isEmpty();
    }
}
