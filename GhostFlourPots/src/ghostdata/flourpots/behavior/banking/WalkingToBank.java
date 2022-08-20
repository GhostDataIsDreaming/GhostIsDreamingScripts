package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.GhostFlourPots;
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
        return GhostFlourPots.selectedWindmillLocation.getBankingLocation().getArea(2);
    }

    public Object onArrive() {
        MethodProvider.log("Arrived");
        return -1;
    }
}
