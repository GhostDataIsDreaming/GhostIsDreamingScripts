package ghostdata.framwork.testing.nodes;

import ghostdata.framework.behaviortree.Node;
import org.dreambot.api.methods.Animations;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FiremakeNode implements Node, PaintListener, ChatListener {

    static Random R = new Random();

    static final Filter<Item> ITEM_FILTER = item -> item.getName().endsWith("logs") || item.getName().startsWith("Logs");

    public static final String TINDERBOX_NAME = "Tinderbox";
    public static final String ASHES_NAME = "Ashes";

    public static Area GRAND_EXCHANGE = new Area(3162, 3487, 3167, 3485);

    private boolean pickupAshes = false;
    protected List<Tile> fireTiles = new ArrayList<>();

    private boolean quick = false;

    boolean makePlayerMove = false;

    public FiremakeNode(boolean pickupAshes) {
        this.pickupAshes = pickupAshes;
    }

    public FiremakeNode() {
        this.pickupAshes = false;
    }

    @Override
    public boolean isValid() {
        return hasTinderbox() && (Inventory.contains(ITEM_FILTER) || !fireTiles.isEmpty());
    }

    @Override
    public Object tick() {
        if (isFireUnderPlayer() || makePlayerMove) {
            Tile emptyTile = getNonFireTile(2, 5, 5);
            if (emptyTile == null) emptyTile = Players.localPlayer().getTile().getArea(5).getRandomTile();

            Walking.walk(emptyTile);
            makePlayerMove = false;
            Tile finalEmptyTile = emptyTile;
            return (Condition) () -> {
                return !Players.localPlayer().isMoving() || Players.localPlayer().getTile().equals(finalEmptyTile);
            };
        }

        if (pickupAshes && !fireTiles.isEmpty()) {
            Tile tile = fireTiles.stream().findFirst().get();
            GameObject fire = GameObjects.getTopObjectOnTile(tile);

            if (fire != null && !fire.getName().equals("Fire")) {
                List<GroundItem> items = GroundItems.getForTile(tile);

                if (!items.isEmpty()) {
                    if (items.stream().anyMatch((f) -> f.getName().equalsIgnoreCase(ASHES_NAME))) {
                        fireTiles.remove(tile);

                        items.stream().forEach((i) -> {
                            i.interact();

                            if (R.nextBoolean()) {
                                MethodProvider.sleep(50, 250);
                            }
                        });

                        return 0;
                    }
                }
            }
        }

        if (!isFiremaking()) {
            if (!quick) {
                if (Inventory.isItemSelected() && Inventory.getSelectedItemName().equalsIgnoreCase(TINDERBOX_NAME)) {
                    Inventory.interact(ITEM_FILTER, "Use");

                    if (pickupAshes) {
                        fireTiles.add(Players.localPlayer().getTile());
                    }

                    quick = R.nextBoolean();

                    return new Object[] {
                            (Condition) () -> !isFireUnderPlayer() && !isFiremaking(), //TODO - Test Out Player#isMoving
                            Calculations.random(3000, 6000)
                    };
                } else {
                    Inventory.interact(TINDERBOX_NAME, "Use");

                    return new Object[] {
                            (Condition) () -> !Inventory.isItemSelected(),
                            250
                    };
                }
            } else {
                Inventory.get(TINDERBOX_NAME).useOn(ITEM_FILTER);
                return new Object[] {
                        (Condition) () -> !isFireUnderPlayer() && !isFiremaking(), //TODO - Test out Player#isMoving
                        Calculations.random(3000, 6000)
                };
            }
        }

        return -1;
    }

    public boolean hasTinderbox() {
        return Inventory.contains(TINDERBOX_NAME);
    }

    public boolean isFireUnderPlayer() {
        GameObject closest = GameObjects.closest("Fire");

        return closest != null && closest.getTile().equals(Players.localPlayer().getTile());
    }

    public Tile getNonFireTile(int searchArea, int tries, int resets) {
        final int _tries = tries;

        Area area = Players.localPlayer().getTile().getArea(searchArea);
        Tile tile = null;

        while (true) {
            if (tries-- == 0) {
                tries = _tries;
                area = Players.localPlayer().getTile().getArea(++searchArea);
            }

            tile = area.getRandomTile();
            GameObject fire = GameObjects.getTopObjectOnTile(tile);

            if (fire != null && fire.getName().equals("Fire")) {
                break;
            }

            if (resets-- <= 0) {
                return null;
            }
        }

        return tile;
    }

    public boolean isFiremaking() {
        return Players.localPlayer().getAnimation() == Animations.FIREMAKING;
    }

    public List<Tile> getFireTiles() {
        return this.fireTiles;
    }

    @Override
    public void onPaint(Graphics graphics) {
        this.fireTiles.forEach((t) -> {
            if (Map.isVisible(t)) {
                graphics.drawPolygon(t.getPolygon());
            }
        });
    }

    @Override
    public void onGameMessage(Message message) {
        if (message.getMessage().equals("You can't light a fire here.")) {
            this.makePlayerMove = true;
        }
    }
}
