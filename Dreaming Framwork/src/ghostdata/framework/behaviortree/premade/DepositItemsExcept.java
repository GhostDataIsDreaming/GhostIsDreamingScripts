package ghostdata.framework.behaviortree.premade;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public class DepositItemsExcept implements Node {

    Filter<Item> filter;

    public DepositItemsExcept(Filter<Item> filter) {
        this.filter = filter;
    }

    @Override
    public boolean isValid() {
        return NPCs.closest("Banker") != null;
    }

    @Override
    public Object tick() {
        if (Bank.isOpen()) {
//            if (filter != null) {
                Bank.depositAllExcept(filter);
//            } else {
//                if (itemNames != null && itemNames.length > 0) {
//                    for (String name : itemNames) {
//                        Bank.depositAllExcept(name);
//                    }
//                }
//            }
            return -1;
        } else {
            Bank.open();
            return (Condition) () -> Bank.isOpen();
        }
    }
}
