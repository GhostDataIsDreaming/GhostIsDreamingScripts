package ghostdata.framework.behaviortree.premade;

import ghostdata.framework.Framework;
import ghostdata.framework.behaviortree.Node;
import ghostdata.framework.utils.WalkingUtils;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.impl.Condition;

import java.awt.*;
import java.util.Random;

public abstract class TilePathWalker implements Node, PaintListener {

    private static final Random R = new Random();

    private final Area finalArea;
    private final Tile[][] availablePaths;

    private boolean reversePath = false;
    private int selectedPath = -1;
    private int pathIndex = 0;

    private Color debugPaintColor;

    public TilePathWalker(Area area) {
        this.finalArea = area;
        this.availablePaths = null;

//        Framework.addListener(this); // We don't have any availablePaths so why even add it?
    }

    public TilePathWalker(Area area, Tile[]... walkingPaths) {
        this.finalArea = area;
        this.availablePaths = walkingPaths;

        Framework.addListener(this);
    }

    public final TilePathWalker reverseTilePath() {
        reversePath = !reversePath;
        return this;
    }

    public final TilePathWalker debugColor(Color color) {
        this.debugPaintColor = color;
        return this;
    }

    @Override
    public final Object tick() {
        if (finalArea.contains(Players.localPlayer().getTile())) {
            selectedPath = -1;
            return onArrival();
        }

        if (selectedPath == -1) {
            selectedPath = Calculations.random(0, availablePaths.length);
            pathIndex = findClosestTileIndex(availablePaths[selectedPath]);
        }

        if (Players.localPlayer().isMoving()) {
            return -1;
        }

        Tile walkTile = null;
        if (availablePaths == null || availablePaths.length == 0) {
            walkTile = R.nextBoolean() ? finalArea.getRandomTile() : finalArea.getTile();
        } else {
            try {
                Tile[] path = !reversePath ? availablePaths[selectedPath] : WalkingUtils.reverse(availablePaths[selectedPath]);

                if (Framework.isDebug()) {
                    MethodProvider.log("Path Length: " + path.length);
                    MethodProvider.log("Step Index: " + pathIndex);
                }
                if (pathIndex >= path.length) {
                    if (Framework.isDebug()) {
                        MethodProvider.logError("Walking Path selected is larger than available path. Finding Closest Tile Index.");
                        MethodProvider.logError("If " + pathIndex + " >= " + path.length + " then change pathIndex to " + path);
                    }

                    pathIndex = findClosestTileIndex(path);
                }

                walkTile = path[pathIndex];
            } catch (Exception e) {
                MethodProvider.logError("There was an error with TilePathWalker. Out of Bounds.");
                MethodProvider.logError(e);
            }
        }

        if (WalkingUtils.walkTile(walkTile != null ? walkTile.getRandomizedTile(3) : finalArea.getRandomTile(), Calculations.random(1, 7))) {
            pathIndex += 1;
            MethodProvider.sleep(1000); //Delay the return
        }

        return new Object[] {
                (Condition) () -> !Players.localPlayer().isMoving(),
                Calculations.random(5000, 10000)
        };
    }

    public abstract Object onArrival();

    private final int findClosestTileIndex(Tile[] tiles) {
        int closestIndex = 0;
        double closestDistance = -1;

        for (int i = 0; i < tiles.length; i++) {
            Tile tile = tiles[i];
            double distance = tile.distance(Players.getLocal().getTile());

            if (distance < closestDistance || closestDistance == -1) {
                closestDistance = distance;
                closestIndex = i;
            }
        }

        return closestIndex;
    }

    @Override
    public final void onPaint(Graphics graphics) {
        if (Framework.isDebug() && availablePaths != null) {
            if (debugPaintColor != null) {
                graphics.setColor(debugPaintColor);
            }

            Tile[] path = availablePaths[this.selectedPath];
            if (path != null && path.length > 0) {
                Tile closest = path[findClosestTileIndex(path)];
                Tile destination = Walking.getDestination();
                Tile current = Players.getLocal().getTile();

                graphics.drawPolygon(current.getPolygon());

                if (Map.isVisible(closest)) {
                    graphics.drawPolygon(closest.getPolygon());
                }
                if (Map.isVisible(destination)) {
                    graphics.drawPolygon(destination.getPolygon());
                }
            }
        }
    }
}
