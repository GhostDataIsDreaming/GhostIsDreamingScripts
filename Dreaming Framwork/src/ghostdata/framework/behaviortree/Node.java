package ghostdata.framework.behaviortree;

public interface Node {

    boolean isValid();

    Object tick();
}
