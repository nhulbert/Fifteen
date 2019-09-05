/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fifteen;
/**
 * Represents the state of a fifteen puzzle at any given point in the solution
 * search.
 * @author Neil Hulbert
 * @version 1.0
 */
public final class Grid implements Comparable<Grid> {
    /**
     * The number of spaces for tiles in the grid.
     */
    public static final int NUM_TILES = 16;

    /**
     * The number of bits used to store each tile.
     */
    public static final int TILE_BITS = 4;

    /**
     * The number of spaces for tiles in each row of the grid.
     */
    public static final int ROW_SIZE = 4;

    /**
     * The number of spaces for tiles in each column of the grid.
     */
    public static final int COLUMN_SIZE = NUM_TILES / ROW_SIZE;

    /**
     * The number of bits used to represent the entire grid.
     */
    public static final int GRID_BITS = TILE_BITS * NUM_TILES;

    /**
     * Holds the contents of the grid.
     */
    private long contents;

    /**
     * Holds the index of the blank tile in the grid.
     */
    private byte blankInd;

    /**
     * A private constructor used to initialize an empty grid.
     */
    private Grid() {
    }

    /**
     * A constructor that produces a grid from a string.
     * @param str a string holding the starting grid tiles in
     * order from left to right, wrapping from top to bottom, with tiles
     * represented as 1-9 and A-F and the empty space as a space (" ")
     * character.
     */
    public Grid(final String str) {
        contents = 0;

        for (byte i = 0; i < str.length(); i++) {
            String tileStr = str.substring(i, i + 1);
            contents <<= TILE_BITS;
            if (tileStr.equals(" ")) {
                blankInd = i;
            } else {
                contents |= Long.parseLong(tileStr, NUM_TILES);
            }
        }
    }

    /**
     * Returns the coordinates for the location of the blank in the grid.
     * @return a two element int array holding the coordinates for the location
     * of the blank in the grid.
     */
    public int[] getBlankLocation() {
        return new int[] {blankInd % ROW_SIZE, blankInd / ROW_SIZE};
    }

    /**
     * Produces a new Grid in which the tile adjacent to the blank location in
     * the given direction within the current Grid is moved into the blank
     * location.
     * @param dir the direction from the blank location that specifies the
     * adjacent tile to be moved.
     * @return a new Grid in which the tile adjacent to the blank location in
     * the given direction within the current Grid is moved into the blank
     * location.
     */
    public Grid move(final int[] dir) {
        int[] blankCoords = getBlankLocation();
        int[] newBlankCoords = new int[] {blankCoords[0] + dir[0],
                                          blankCoords[1] + dir[1]};

        if (newBlankCoords[0] < 0 || newBlankCoords[0] >= ROW_SIZE
         || newBlankCoords[1] < 0 || newBlankCoords[1] >= COLUMN_SIZE) {
            return null;
        }

        Grid res = new Grid();

        res.blankInd = (byte) (newBlankCoords[1] * ROW_SIZE
                             + newBlankCoords[0]);

        int shift1 = ((NUM_TILES - 1 - blankInd) * TILE_BITS);
        int shift2 = ((NUM_TILES - 1 - res.blankInd) * TILE_BITS);
        long mask2 = ((1 << TILE_BITS) - 1);
        mask2 <<= shift2;
        long newTileLoc = (shift1 > shift2)
                            ? (contents & mask2) << (shift1 - shift2)
                            : (contents & mask2) >>> (shift2 - shift1);

        res.contents = (contents & ~mask2) | newTileLoc;

        return res;
    }

    /**
     * Generates a tile index map that allows one to look up the location of
     * each tile within the grid.
     * @return a 2D byte array that holds the index of the nth tile ordered
     * 0-9, A-F (with tile 0 being the blank location) at index n-1.
     */
    public byte[][] getTileIndexMap() {
        byte[][] tileIndexMap = new byte[NUM_TILES][];

        long cur = contents;
        long mask = (1 << TILE_BITS) - 1;

        for (int i = 0; i < NUM_TILES; i++) {
            int ind = NUM_TILES - i - 1;
            tileIndexMap[(int) (cur & mask)] =
                    new byte[] {(byte) (ind % ROW_SIZE),
                                (byte) (ind / ROW_SIZE)};
            cur >>>= TILE_BITS;
        }

        return tileIndexMap;
    }

    /**
     * Gets the tile at the given location.
     * @param location the coordinates for the location of the tile to retrieve.
     * @return an integer representing which tile is at the given location in
     * the grid, with the tiles 0-9, A-F (with tile 0 being the blank
     * location) represented as 0-15 respectively.
     */
    public int getTileAt(final int[] location) {
        int locInd = location[1] * ROW_SIZE + location[0];
        long mask = (1 << TILE_BITS) - 1;

        int shift = (NUM_TILES - 1 - locInd) * TILE_BITS;
        return (int) (mask & (contents >>> shift));
    }

    @Override
    public boolean equals(final Object oth) {
        if (oth == this) {
            return true;
        }

        if (oth == null) {
            return false;
        }

        if (oth.getClass() != getClass()) {
            return false;
        }

        return contents == ((Grid) oth).contents;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(contents);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        long cur = contents;
        long mask = (1 << TILE_BITS) - 1;

        for (int i = 0; i < NUM_TILES; i++) {
            if (i % ROW_SIZE == 0) {
                sb.append("\n");
            }
            String str = Long.toString(cur & mask, NUM_TILES);
            if (str.equals("0")) {
                str = " ";
            }
            sb.append("|" + str);
            cur >>>= TILE_BITS;
        }

        return sb.reverse().toString();
    }

    @Override
    public int compareTo(final Grid o) {
        return Long.compare(contents, o.contents);
    }
}
