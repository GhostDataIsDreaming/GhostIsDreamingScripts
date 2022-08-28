package ghostdata.flourpots.behavior.materials.gains;

import ghostdata.flourpots.ScriptStats;
import ghostdata.flourpots.ScriptStep;
import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.GameObject;

public class PickGrains implements Node {

    private static final int WHEAT_ENTITY_ID = 15506;
    private static final String WHEAT_ENTITY_NAME = "Wheat";
    private static final String INTERACT = "Pick";
    private static final int ANIMATION_ID = 827;

    public int maxGrainsToPick;
    private boolean randomStart = false;

    public PickGrains(int grainsToPick) {
        this.maxGrainsToPick = grainsToPick;
    }

    @Override
    public boolean isValid() {
        return ScriptStats.CURRENT_STEP == ScriptStep.PICK_GRAINS && maxGrainsToPick >= 0;
    }

    @Override
    public Object tick() {
        if (maxGrainsToPick-- <= 0 || Inventory.isFull()) {
            ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_BANK;
            return -1;
        }

        if (Players.localPlayer().getAnimation() == ANIMATION_ID) {
            return -1;
        }

        if (!randomStart) {
            Area area = ScriptStats.WIMDMILL_LOCATION.getGrainLocation().area;

            Walking.walk(area.getRandomTile());
            randomStart = true;
        }


        GameObject wheat = GameObjects.closest(WHEAT_ENTITY_ID);
        if (wheat == null) wheat = GameObjects.closest(WHEAT_ENTITY_NAME);

        wheat.interact(INTERACT);
        ScriptStats.GRAINS_COLLECTED++;

        randomStart = Calculations.random(0, 100) <= Calculations.random(0, 100); //Random Start with random percentage.... Start in random pot in the grains.

        int min = Calculations.random(100, 1000);
        int max = Calculations.random(min + 1, min * Calculations.random(1, 5));

        return Calculations.random(min, max);
    }
}
