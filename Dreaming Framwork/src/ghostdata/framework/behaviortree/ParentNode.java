package ghostdata.framework.behaviortree;

import org.dreambot.api.methods.MethodProvider;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public abstract class ParentNode implements Node {

    protected final List<Node> children;

    public ParentNode() {
        this.children = new LinkedList<>();
    }

    public ParentNode(Node... nodes) {
        this();

        addNodes(nodes);
    }

    public ParentNode(List<Node> nodes) {
        this();

        for (Node node : nodes) {
            addNode(node);
        }
    }

    public final ParentNode addNode(Node node) {
        children.add(node);
        return this;
    }

    public final ParentNode addNodes(Node... nodes) {
        Collections.addAll(this.children, nodes);
        return this;
    }

    @Override
    public final Object tick() {
        return children.stream()
                .filter(new Predicate<Node>() {
                    @Override
                    public boolean test(Node node) {
                        boolean is = node != null && node.isValid();
                        MethodProvider.log("Filtering " + node.getClass().getSimpleName() + " - " + is);

                        return is;
                    }
                })
                .findAny()
                .map(l -> {
                        MethodProvider.log("Ticking " + l.getClass().getSimpleName());
                        return l.tick();
                    })
                .orElse(-1);
    }
}
