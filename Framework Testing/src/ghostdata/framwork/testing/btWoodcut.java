package ghostdata.framwork.testing;

import ghostdata.framework.behaviortree.BehaviorTree;
import ghostdata.framework.behaviortree.premade.DepositSelectedItems;
import ghostdata.framework.behaviortree.premade.WalkToArea;
import ghostdata.framwork.testing.nodes.WoodcutNode;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(category = Category.WOODCUTTING, name = "btWoodcut", author = "GhostData", version = 0.0)
public class btWoodcut extends AbstractScript {

    public static BehaviorTree btTree;
    int maxRadius = 10;

    @Override
    public void onStart() {
        btTree = new BehaviorTree();
    }

    private static final Filter<Item> LOG_FILTER = new Filter<Item>() {
        @Override
        public boolean match(Item item) {
            return item.getName().toLowerCase().contains("logs");
        }
    };

    @Override
    public int onLoop() {
        if (!btTree.hasBranches()) {
            Area startingArea = Players.localPlayer().getTile().getArea(maxRadius);
            Area bankArea = Bank.getClosestBankLocation().getArea(2);

            btTree.addNode(new WalkToArea(startingArea) {
                @Override
                public boolean isValid() {
                    return !Inventory.isFull() && !startingArea.contains(Players.localPlayer().getTile());
                }
            });

            btTree.addNode(new WalkToArea(bankArea) {
                @Override
                public boolean isValid() {
                    return Inventory.isFull() && NPCs.closest("Banker") == null;
                }
            });

//            btTree.addNode(new DepositSelectedItems("Logs", "Oak logs", "Willow logs", "Yew logs") {
            btTree.addNode(new DepositSelectedItems(LOG_FILTER) {
                @Override
                public boolean isValid() {
                    return super.isValid() && Inventory.isFull();
                }
            });

            btTree.addNode(new WoodcutNode(startingArea));

            return btTree.defaultSleepTimer();
        }

        return btTree.tick();
    }
}
