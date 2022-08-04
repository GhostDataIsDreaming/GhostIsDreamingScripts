package ghostdata.framework.behaviortree.premade;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

import java.util.ArrayList;
import java.util.List;

public class DepositSelectedItems implements Node {

    String[] itemNames;
    int[] itemIds;

    Filter<Item> filter;

    public DepositSelectedItems(String... itemNames) {
        this.itemNames = itemNames;
    }
    
    public DepositSelectedItems(int... itemIds) {
        this.itemIds = itemIds;
    }
    
    public DepositSelectedItems(Object... idsOrNames) {
        List<String> itemNames = new ArrayList();
        List<Integer> itemIds = new ArrayList();
        
        for (Object obj : idsOrNames) {
            String str = obj.toString();
            
            try {
                int val = Integer.parseInt(str);
                itemIds.add(val);
            } catch (NumberFormatException e) {
                itemNames.add(str);
            }
        }
        
        if (!itemNames.isEmpty()) this.itemNames = itemNames.toArray(new String[itemNames.size()]);
        if (!itemIds.isEmpty()) {
            this.itemIds = new int[itemIds.size()];
            for (int i = 0; i < itemIds.size(); i++) {
                this.itemIds[i] = itemIds.get(i);
            }
        }
    }

    public DepositSelectedItems(Filter<Item> filter) {
        this.filter = filter;
    }

    @Override
    public boolean isValid() {
        return NPCs.closest("Banker") != null;
    }

    @Override
    public Object tick() {
        if (Bank.isOpen()) {
            if (filter != null) {
                Bank.depositAll(filter);
            } else {
                if (itemNames != null && itemNames.length > 0) {
                    for (String name : itemNames) {
                        Bank.depositAll(name);
                        MethodProvider.sleep(100, 500);
                    }
                }

                if (itemIds != null && itemIds.length > 0) {
                    for (int id : itemIds) {
                        Bank.depositAll(id);
                        MethodProvider.sleep(100, 500);
                    }
                }
            }

            Bank.close();
        } else {
            Bank.open();
            return (Condition) () -> {
                return Bank.isOpen();
            };
        }

        return -1;
    }
}
