package ghostdata.framework.behaviortree.premade;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public class WithdrawSelectedItem implements Node {

    String itemName;
    int itemId = -1;
    Filter<Item> filter;

    int amount;

    public WithdrawSelectedItem(String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
    }

    public WithdrawSelectedItem(int itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public WithdrawSelectedItem(Filter<Item> filter, int amount) {
        this.filter = filter;
        this.amount = amount;
    }

    @Override
    public boolean isValid() {
        return NPCs.closest("Banker") != null;
    }

    @Override
    public Object tick() {
        if (Bank.isOpen()) {
            if (amount > 0) {
                if (filter != null) {
                    Bank.withdraw(filter, amount);
                } else {
                    if (itemName != null && !itemName.isEmpty()) {
                        Bank.withdraw(itemName, amount);
                    } else if (itemId != -1) {
                        Bank.withdraw(itemId, amount);
                    }
                }
            } else {
                 if (filter != null) {
                     Bank.withdrawAll(filter);
                 } else if (itemName != null && !itemName.isEmpty()) {
                     Bank.withdrawAll(itemName);
                 } else if (itemId != -1) {
                     Bank.withdrawAll(itemId);
                 }
            }

            Bank.close();
        } else {
            Bank.open();
            return (Condition) () -> Bank.isOpen();
        }

        return -1;
    }
}
