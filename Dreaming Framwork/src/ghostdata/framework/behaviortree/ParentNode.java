package ghostdata.framework.behaviortree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class ParentNode implements Node {

    protected RootNode foundRootNode;
    protected ParentNode parent;
    protected final List<Node> children;

    public ParentNode() {
        this.children = new LinkedList<>();
    }

    public ParentNode(Node... leaves) {
        this();

        addNodes(leaves);
    }

    public ParentNode(List<Node> leaves) {
        this();

        for (Node leaf : leaves) {
            this.children.add(leaf);
        }
    }

    public final ParentNode addNode(Node leaf) {
        children.add(leaf);
        return this;
    }

    public final ParentNode addNodes(Node... leaves) {
        Collections.addAll(this.children, leaves);
        return this;
    }

    @Override
    public final Object tick() {
        if (foundRootNode == null) {
            int defaultSleep = -1;
            ParentNode crawler = parent;
            ParentNode selected = this;

            while (crawler != null && !(selected instanceof RootNode)) {
                crawler = parent.parent;
                selected = parent;
            }

            foundRootNode = (RootNode) selected;
        }

        return children.stream()
                .filter(l -> l != null && l.isValid())
                .findAny()
                .map(l -> {
                        foundRootNode.BTree.selectedParentNode = this;
                        foundRootNode.BTree.selectedLeaf = l;

                        return l.tick();
                    })
                .orElse(foundRootNode.BTree.defaultSleepTimer());
    }
}
