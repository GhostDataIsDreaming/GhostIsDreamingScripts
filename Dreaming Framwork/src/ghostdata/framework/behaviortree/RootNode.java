package ghostdata.framework.behaviortree;

public class RootNode extends ParentNode {
    protected BTree BTree;

    @Override
    public final boolean isValid() {
        return true;
    }
}
