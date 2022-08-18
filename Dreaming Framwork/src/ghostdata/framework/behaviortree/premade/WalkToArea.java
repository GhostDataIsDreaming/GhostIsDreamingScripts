package ghostdata.framework.behaviortree.premade;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;

@Deprecated
public abstract class WalkToArea extends WalkTo {

    Area startingArea;

    public WalkToArea(Area area) {
        this.startingArea = area;
    }

    public Area getArea() {
        return this.startingArea;
    }
}
