package ghostdata.framework.utils;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;

public class WalkingUtils {

    public static Tile[] reverse(Tile[] tiles) {
        Tile[] reversed = new Tile[tiles.length];
        int tilesIndex = tiles.length;

        for (int i = 0; i < tiles.length; i++) {
            reversed[tilesIndex - 1] = tiles[i];
            tilesIndex = tilesIndex - 1;
        }

        return reversed;
    }

    public static Tile[][] reverseMulti(Tile[][] tilePaths) {
        Tile[][] reversed = new Tile[tilePaths.length][];
        int tilePathsIndex = tilePaths.length;

        for (int i = 0; i < tilePaths.length; i++) {
            reversed[tilePathsIndex - 1] = reverse(tilePaths[i]);
            tilePathsIndex = tilePathsIndex - 1;
        }

        return reversed;
    }

    public static boolean walkTile(Tile tile, int shouldWalk) {
        return walkTile(tile, shouldWalk, true);
    }

    public static boolean walkTile(Tile tile, int shouldWalk, boolean findClosest) {
        if (Map.isTileOnMap(tile)) {
            if (Walking.shouldWalk(shouldWalk)) {
                int bounds = Calculations.random(0, 100);

                if (bounds <= 25) {
                    return Map.interact(tile, "Walk here");
                } else if (bounds >= 75) {
                    return Walking.clickTileOnMinimap(tile);
                } else {
                    return Walking.walk(tile);
                }
            }
        } else if (findClosest) {
            return Walking.walk(tile.getRandomizedTile(2));
        }

        return false;
    }
}
