package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.behavior.BankingNodeParent;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public class OpenBank implements Node {

    static final String[] names = { "Banker", "Bank booth" };
    static final String INTERACT = "Bank";

    @Override
    public boolean isValid() {
        return !Bank.isOpen() && NPCs.closest("Banker") != null;
    }

    @Override
    public Object tick() {
        if (Calculations.random(0, 100) <= 25) {
            Bank.open(); // TODO - Swap Between using hotkeys and interacting
        } else {
            NPC bankerNpc;

            if (Calculations.random(0, 100) <= 75) {
                bankerNpc = NPCs.closest(names[1]);
            } else {
                bankerNpc = NPCs.closest(names[0]);
            }

            if (bankerNpc == null) {
                Walking.walk(GhostFlourPots.selectedWindmillLocation.getBankingLocation().getTile());
            } else {
                bankerNpc.interact(INTERACT);
            }
        }

        return (Condition) () -> Bank.isOpen();
    }
}
