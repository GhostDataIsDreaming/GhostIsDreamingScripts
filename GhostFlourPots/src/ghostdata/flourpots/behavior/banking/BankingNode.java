package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.behavior.materials.gains.PickGrains;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankingNode implements Node {

    static final String[] names = { "Bank booth", "Banker" };
    static final String INTERACT = "Bank";
    static final int WITHDRAW_AMOUNT = 14;
    static final List<FlourPotItems> WITHDRAW_ITEMS = new ArrayList() {
        {
            add(FlourPotItems.POT);
            add(FlourPotItems.GRAIN);
        }
    };

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.BANKING;
    }

    @Override
    public Object tick() {
        if (!Bank.isOpen()) {
            if (Calculations.random(0, 100) <= 25) {
                Bank.open();
            } else {
                NPC bankerNpc;

                if (Calculations.random(0, 100) <= 75) {
                    bankerNpc = NPCs.closest(names[1]);
                } else {
                    bankerNpc = NPCs.closest(names[0]);
                }

                if (bankerNpc == null) {
                    Walking.walk(ScriptStats.WIMDMILL_LOCATION.getBankLocation().getTile());
                } else {
                    bankerNpc.interact(INTERACT);
                }
            }

            return (Condition) () -> Bank.isOpen();
        } else {
            if (!Inventory.isEmpty()) {
                Bank.depositAllItems(); // TODO - Change from DepositAll to selectItem -> deposit
                MethodProvider.sleep(250, 1000);
            }

            Collections.shuffle(WITHDRAW_ITEMS);

            for (FlourPotItems item : WITHDRAW_ITEMS) {
                Bank.withdraw(item.id, WITHDRAW_AMOUNT);
                MethodProvider.sleep(250, 750);
            }

            if (GhostFlourPots.getInstance()._windmillLocationSelector.grainPickerEnabledRadioButton.isSelected()) {
                PickGrains grainsNode = (PickGrains) GhostFlourPots.getInstance().bTree.getNode(PickGrains.class).findFirst().get();
                if (grainsNode.maxGrainsToPick >= 1) {
                    ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_GRAINS;
                } else {
                    ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_WINDMILL;
                }

            } else {
                if (!(Inventory.contains(FlourPotItems.POT.id) || Inventory.contains(FlourPotItems.GRAIN.id))) {
                    ScriptStats.CURRENT_STEP = ScriptStep.NO_REQUIREMENTS;
                } else {
                    ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_WINDMILL;
                }
            }

            Bank.close();
            return (Condition) () -> !Bank.isOpen();
        }
    }
}
