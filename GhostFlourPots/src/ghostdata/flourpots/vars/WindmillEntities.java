package ghostdata.flourpots.vars;

import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.List;

public enum WindmillEntities {

    HOPPER(24961, "Hopper"),
    HOPPER_CONTROLS(24962, "Hopper controls"),
    FLOUR_BIN(1781, "Flour bin")
    ;

    int id;
    String name;

    private final Filter<NPC> filter;

    WindmillEntities(int id, String name) {
        this.id = id;
        this.name = name;

        this.filter = npc -> npc.getID() == id || npc.getName().equalsIgnoreCase(name);
    }

    public NPC getClosest() {
        return NPCs.closest(this.id);
    }

    public List<NPC> getClosestWithFilter() {
        return NPCs.all(filter);
    }
}
