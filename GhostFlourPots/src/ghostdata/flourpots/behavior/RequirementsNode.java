package ghostdata.flourpots.behavior;

import ghostdata.flourpots.GhostFlourPots;
import ghostdata.flourpots.ScriptStep;
import ghostdata.flourpots.vars.FlourPotItems;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Timer;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class RequirementsNode implements Node {

    private static final long CHECK_INTERVAL = TimeUnit.SECONDS.toMillis(10);
    long lastChecked = System.currentTimeMillis();

    @Override
    public boolean isValid() {
        return Bank.isOpen() && ((lastChecked - System.currentTimeMillis()) >= CHECK_INTERVAL);
    }

    @Override
    public Object tick() {
        if (
                !(Bank.contains(FlourPotItems.POT.id) && Bank.contains(FlourPotItems.GRAIN.id)) &&
                !(Inventory.contains(FlourPotItems.POT.id) && Inventory.contains(FlourPotItems.GRAIN.id))
        ) {
            GhostFlourPots.currentStep = ScriptStep.NO_REQUIREMENTS;
            MethodProvider.log("Ran out of either Grains or Pots. Script Pausing.");
            ScriptManager.getScriptManager().pause();
        }

        lastChecked = System.currentTimeMillis();

        return -1;
    }
}
