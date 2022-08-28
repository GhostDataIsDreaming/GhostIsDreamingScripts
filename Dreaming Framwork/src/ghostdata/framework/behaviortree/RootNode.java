package ghostdata.framework.behaviortree;

public class RootNode extends ParentNode {
    protected BehaviorTree BehaviorTree;

    @Override
    public final boolean isValid() {
        return true;
    }
}
