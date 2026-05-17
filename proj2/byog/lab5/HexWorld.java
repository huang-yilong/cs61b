package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /** Simple struct for positions (x, y). p is the lower-left corner of the hexagon. */
    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Width of row i (0-indexed from bottom) for a hexagon of side length s.
     * The widest part has two rows of equal length.
     */
    private static int rowWidth(int s, int i) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon side length must be >= 2");
        }
        int height = 2 * s;
        if (i < 0 || i >= height) {
            throw new IllegalArgumentException("Row index out of range");
        }
        // distances grow until (s-1), then stay max for two rows, then shrink.
        int d = (i < s) ? i : (height - 1 - i);
        int cap = Math.min(d, s - 1);
        return s + 2 * cap;
    }

    /**
     * Horizontal x-offset (from p.x) of row i (0-indexed from bottom).
     */
    private static int rowXOffset(int s, int i) {
        int d = (i < s) ? i : (2 * s - 1 - i);
        int cap = Math.min(d, s - 1);
        return (s - 1) - cap;
    }

    /** Draws a single horizontal row of tiles starting at (x, y). */
    private static void drawRow(TETile[][] world, int x, int y, int length, TETile t) {
        if (world == null || t == null) {
            throw new IllegalArgumentException("world/tile cannot be null");
        }
        if (y < 0 || y >= world[0].length) {
            return;
        }
        for (int dx = 0; dx < length; dx += 1) {
            int wx = x + dx;
            if (wx < 0 || wx >= world.length) {
                continue;
            }
            world[wx][y] = t;
        }
    }

    /**
     * Adds a hexagon of side length s to the world, with its lower-left corner at p.
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (p == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon side length must be >= 2");
        }
        int height = 2 * s;
        for (int i = 0; i < height; i += 1) {
            int y = p.y + i;
            int x = p.x + rowXOffset(s, i);
            int w = rowWidth(s, i);
            drawRow(world, x, y, w, t);
        }
    }

    /**
     * Neighbor whose bottom-left is up-right of p.
     * For side length s, moving to this neighbor shifts by (+ (2s-1), + s).
     */
    private static Position topRightNeighbor(Position p, int s) {
        return new Position(p.x + (2 * s - 1), p.y + s);
    }

    /**
     * Neighbor whose bottom-left is down-right of p.
     * For side length s, moving to this neighbor shifts by (+ (2s-1), - s).
     */
    private static Position bottomRightNeighbor(Position p, int s) {
        return new Position(p.x + (2 * s - 1), p.y - s);
    }

    /** Returns the bottom neighbor (same x) in a vertical column. */
    private static Position bottomNeighbor(Position p, int s) {
        return new Position(p.x, p.y - 2 * s);
    }

    /** Picks a random tile type for a "biome". */
    private static TETile randomBiomeTile(Random r) {
        int k = r.nextInt(5);
        if (k == 0) {
            return Tileset.FLOWER;
        } else if (k == 1) {
            return Tileset.GRASS;
        } else if (k == 2) {
            return Tileset.TREE;
        } else if (k == 3) {
            return Tileset.MOUNTAIN;
        }
        return Tileset.SAND;
    }

    /** Draws N hexagons vertically downward starting from topPos (which is the top hex's bottom-left). */
    private static void drawRandomVerticalHexes(TETile[][] world, Position topPos, int n, int s, Random r) {
        Position cur = topPos;
        for (int i = 0; i < n; i += 1) {
            addHexagon(world, cur, s, randomBiomeTile(r));
            cur = bottomNeighbor(cur, s);
        }
    }

    /** Draws the tessellated 19-hex world: columns with counts 3,4,5,4,3. */
    private static void drawTessellatedWorld(TETile[][] world, Position topLeft, int s, long seed) {
        Random r = new Random(seed);
        int[] counts = new int[]{3, 4, 5, 4, 3};

        Position colTop = topLeft;
        for (int c = 0; c < counts.length; c += 1) {
            drawRandomVerticalHexes(world, colTop, counts[c], s, r);
            // Next column's top alternates between bottomRight and topRight.
            if (c % 2 == 0) {
                colTop = bottomRightNeighbor(colTop, s);
            } else {
                colTop = topRightNeighbor(colTop, s);
            }
        }
    }

    /**
     * Public entry point for generating the 19-hex tessellated world (3,4,5,4,3 columns).
     * This is useful for reusing the lab5 world generator from the main BYOG game.
     */
    public static TETile[][] generateTessellatedWorld(int width, int height, int s, long seed) {
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // A reasonable default that fits for typical sizes (e.g. 80x30 or larger).
        Position topLeft = new Position(5, height - (2 * s + 1));
        drawTessellatedWorld(world, topLeft, s, seed);
        return world;
    }

    @Test
    public void testRowWidthAndOffset() {
        // s=2: widths should be [2,4,4,2], offsets [1,0,0,1]
        assertArrayEquals(new int[]{2, 4, 4, 2}, new int[]{rowWidth(2, 0), rowWidth(2, 1), rowWidth(2, 2), rowWidth(2, 3)});
        assertArrayEquals(new int[]{1, 0, 0, 1}, new int[]{rowXOffset(2, 0), rowXOffset(2, 1), rowXOffset(2, 2), rowXOffset(2, 3)});

        // s=3: widths [3,5,7,7,5,3], offsets [2,1,0,0,1,2]
        assertArrayEquals(new int[]{3, 5, 7, 7, 5, 3}, new int[]{rowWidth(3, 0), rowWidth(3, 1), rowWidth(3, 2), rowWidth(3, 3), rowWidth(3, 4), rowWidth(3, 5)});
        assertArrayEquals(new int[]{2, 1, 0, 0, 1, 2}, new int[]{rowXOffset(3, 0), rowXOffset(3, 1), rowXOffset(3, 2), rowXOffset(3, 3), rowXOffset(3, 4), rowXOffset(3, 5)});
    }

    /** Simple visual sanity check. */
    public static void main(String[] args) {
        int width = 80;
        int height = 60;
        int s = 3;

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // Pick a position that leaves enough room for the full 19-hex layout.
        Position topLeft = new Position(5, 40);
        drawTessellatedWorld(world, topLeft, s, 20260516L);

        ter.renderFrame(world);
    }
}
