package ghostdata.framework.behaviortree.premade;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;

public abstract class WalkToArea implements Node {

    Area startingArea;

    public WalkToArea(Area area) {
        this.startingArea = area;
    }

    @Override
    public Object tick() {
        Tile tile = startingArea.getRandomTile();

        Walking.walk(tile);

        return (Condition) () -> Walking.getDestinationDistance() <= 2;
    }
}
