package ghostdata.framework.behaviortree.premade;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.impl.Condition;

public abstract class WalkTo implements Node {

    @Override
    public Object tick() {
        Tile tile = getArea().getRandomTile();

        if (getArea().contains(Players.localPlayer().getTile())) {
            return onArrive();
        } else {
            Walking.walk(tile);

            return new Object[]{
                    (Condition) () -> Walking.getDestinationDistance() <= getWaitDistance(),
                    10000
            };
        }
    }

    public abstract Area getArea();

    public int getWaitDistance() {
        return 2;
    }

    public abstract Object onArrive();
}
