package ghostdata.framwork.testing;

import ghostdata.framework.FrameworkScript;
import ghostdata.framework.behaviortree.BehaviorTree;
import ghostdata.framework.behaviortree.premade.WalkToArea;
import ghostdata.framework.behaviortree.premade.WithdrawSelectedItem;
import ghostdata.framwork.testing.nodes.FiremakeNode;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.core.Instance;


@ScriptManifest(category = Category.FIREMAKING, name = "btFiremake", author = "GhostData", version = 0.0)
public class btFiremake extends FrameworkScript {
    public static BehaviorTree btTree;

    static Area GRAND_EXCHANGE = new Area(3142, 3512, 3184, 3470);;

    FiremakeNode firemakeNode;

    @Override
    public void onStart() {
        btTree = new BehaviorTree();
    }

    @Override
    public int onLoop() {
        if (!btTree.hasBranches()) {
            firemakeNode = new FiremakeNode(false);
            Instance.getInstance().addEventListener(firemakeNode);
//            this.addChatListener(firemakeNode);
//            this.addPaintListener(firemakeNode);

            btTree.addNode(new WalkToArea(GRAND_EXCHANGE) {
                @Override
                public Object onArrive() {
                    return -1;
                }

                @Override
                public boolean isValid() {
                    return !GRAND_EXCHANGE.contains(Players.localPlayer().getTile());
                }
            });

            btTree.addNode(new WithdrawSelectedItem(FiremakeNode.TINDERBOX_NAME, 1) {
                @Override
                public boolean isValid() {
                    return super.isValid() && !Inventory.contains(FiremakeNode.TINDERBOX_NAME);
                }
            });

            btTree.addNode(new WithdrawSelectedItem("Logs", -1) {
                @Override
                public boolean isValid() {
                    return super.isValid() && !Inventory.contains("Logs");
                }
            });

            btTree.addNode(firemakeNode);
            return btTree.defaultSleepTimer();
        }

        return btTree.tick();
    }
}
