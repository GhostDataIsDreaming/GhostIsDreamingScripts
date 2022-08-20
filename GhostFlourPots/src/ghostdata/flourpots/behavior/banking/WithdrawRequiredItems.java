package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WithdrawRequiredItems implements Node {

    static final int WITHDRAW_AMOUNT = 14;
    static final List<FlourPotItems> WITHDRAW_ITEMS = new ArrayList() {
        {
            add(FlourPotItems.POT);
            add(FlourPotItems.GRAIN);
        }
    };

    @Override
    public boolean isValid() {
        return Bank.isOpen() && Inventory.isEmpty();
    }

    @Override
    public Object tick() {
        Collections.shuffle(WITHDRAW_ITEMS);

        for (FlourPotItems item : WITHDRAW_ITEMS) {
            Bank.withdraw(item.id, WITHDRAW_AMOUNT);
            MethodProvider.sleep(100, 500);
        }

        return 0;
    }
}
