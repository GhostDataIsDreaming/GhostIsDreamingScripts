package ghostdata.framework.behaviortree;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

import java.util.stream.Stream;

public class BTree {

    protected final RootNode rootNode;

    protected ParentNode selectedParentNode;
    protected Node selectedLeaf;

    public BTree() {
        this.rootNode = new RootNode();
        this.rootNode.BTree = this;
    }

    public BTree(RootNode rootNode) {
        this.rootNode = rootNode;
        this.rootNode.BTree = this;
    }

    public int defaultSleepTimer() {
        int min = Calculations.random(100, 1000);
        int max = Calculations.random(min + 1, min * 5);

        return Calculations.random(min, max);
    }

    public final BTree addNode(Node node) {
        rootNode.addNodes(node);
        return this;
    }

    public final BTree addNodes(Node... nodes) {
        rootNode.addNodes(nodes);
        return this;
    }

    public int onConditionDoWait() {
        return defaultSleepTimer();
    }

    public void onUnknownTickObject(Object tick) {
        MethodProvider.logError("Returned unknownTickObject[" + tick.getClass() + "] - toString:" + tick.toString());
        MethodProvider.logError("Override onUnknownTickObject in BTree");
    }

    public final ParentNode getSelectedBranch() {
        return selectedParentNode;
    }

    public final void clear() {
        rootNode.children.clear();
    }

    public final Node getSelectedLeaf() {
        return selectedLeaf;
    }

    public final boolean hasBranches() {
        return !rootNode.children.isEmpty();
    }

    public final int tick() {
        Object tick = rootNode.tick();

        if (tick != null) {
            if (tick instanceof Object[]) {
                Condition condition = (Condition) ((Object[]) tick)[0];
                int wait = (int) ((Object[]) tick)[1];

                MethodProvider.sleepUntil(condition, wait == -1 ? onConditionDoWait() : wait);
            } else if (tick instanceof Condition) {
                MethodProvider.sleepUntil((Condition) tick, onConditionDoWait());
            } else if (tick.getClass() == int.class || tick.getClass() == Integer.class) {
                int i = (int) tick;

                return i < 0 ? defaultSleepTimer() : i;
            } else {
                onUnknownTickObject(tick);
            }
        } else {
            onUnknownTickObject(null);
        }

        return Calculations.random(100, 1000);
    }

    public Stream<Node> getNode(Class<? extends Node> nodeClass) {
        return rootNode.children.stream().filter((n) -> { return n.getClass() == nodeClass; });
    }
}
