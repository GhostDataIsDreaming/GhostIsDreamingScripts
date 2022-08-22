package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.ScriptStats;
import ghostdata.framework.behaviortree.premade.WalkTo;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.map.Area;

public class WalkingToBank extends WalkTo {

    @Override
    public boolean isValid() {
        return NPCs.closest("Banker") == null;
    }

    @Override
    public Area getArea() {
        return ScriptStats.WIMDMILL_LOCATION.getBankLocation().getArea(2);
    }

    public Object onArrive() {
        MethodProvider.log("Arrived");
        return -1;
    }
}
