package ghostdata.flourpots.behavior.banking;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.framework.behaviortree.premade.WalkTo;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;

public class WalkingToBank extends WalkTo {

    @Override
    public boolean isValid() {
//        return NPCs.closest("Banker") == null || !getArea().contains(Players.localPlayer().getTile());
        return ScriptStats.CURRENT_STEP == ScriptStep.WALKING_TO_BANK;
    }

    @Override
    public Area getArea() {
        return ScriptStats.WIMDMILL_LOCATION.getBankLocation().getArea(2);
    }

    public Object onArrive() {
        ScriptStats.CURRENT_STEP = ScriptStep.BANKING;
        return -1;
    }
}
