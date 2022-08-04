package ghostdata.framwork.testing.nodes;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.Animations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WoodcutNode implements Node {
    static final List<Integer> WOODCUT_ANIMATIONS = new ArrayList<>() {
        {
            for (Field f : Animations.class.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers()) && f.getName().startsWith("WOODCUTTING")) {
                    try {
                        add((int) f.get(null));
                    } catch (Exception e) {
                        MethodProvider.logError(e);
                    }
                }
            }
        }
    };

    static Random R = new Random();

    Area woodcutArea;

    String[] trees = { "Tree", "Oak tree", "Willow tree", "Maple tree", "Yew" };

    public WoodcutNode(Area woodcutArea) {
        this.woodcutArea = woodcutArea;
    }

    public WoodcutNode(Area woodcutArea, String... trees) {
        this.trees = trees;
        this.woodcutArea = woodcutArea;
    }

    @Override
    public boolean isValid() {
        return woodcutArea.contains(Players.localPlayer().getTile());
    }

    @Override
    public Object tick() {
        Player me = Players.localPlayer();

        if (me.getAnimation() == Animations.IDLE && !me.isMoving()) {
            GameObject treeObject = GameObjects.closest(trees);

            if (treeObject != null) {
                if (R.nextBoolean()) {
                    treeObject.interact();
                } else {
                    treeObject.interact("Chop down");
                }

                return new Object[] {
                        (Condition) () -> !WOODCUT_ANIMATIONS.contains(me.getAnimation()),
                        5000
                };
            }
        }

        return -1;
    }
}
