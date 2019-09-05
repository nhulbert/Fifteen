/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe.heuristic;

import java.util.ArrayList;
import java.util.List;
import fifteen.Grid;

/**
 * A heuristic that defines distance to a goal by the number of tiles
 * misplaced with respect to the goal.
 * @author Neil Hulbert
 * @version 1.0
 */
public class H1Heuristic implements Heuristic {
    /**
     * Set to true to assert that the value returned by deltaCompute is the same
     * as the value returned by fullCompute each time deltaCompute is called.
     */
    public static final boolean DEBUG_DELTA = false;

    @Override
    public List<Integer> fullCompute(final Grid grid,
                                           final byte[][][] goalInds) {
        List<Integer> out = new ArrayList<>(goalInds.length);

        for (int i = 0; i < goalInds.length; i++) {
            int accum = 0;

            for (int h = 0; h < Grid.NUM_TILES; h++) {
                int[] loc = {h % Grid.ROW_SIZE, h / Grid.ROW_SIZE};
                int tile = grid.getTileAt(loc);
                if (tile != 0) { // 0 is the blank tile
                    byte[] goalLoc = goalInds[i][tile];

                    accum += (loc[0] == goalLoc[0]
                           && loc[1] == goalLoc[1]) ? 0 : 1;
                }
            }

            out.add(accum);
        }

        return out;
    }

    @Override
    public List<Integer> deltaCompute(final Grid grid,
                                            final byte[][][] goalInds,
                                            final List<Integer> prevHScores,
                                            final int[] prevBlankCoord) {
        List<Integer> out = new ArrayList<>(goalInds.length);

        for (int i = 0; i < goalInds.length; i++) {
            int[] curBlankCoord = grid.getBlankLocation();

            int movedTile = grid.getTileAt(prevBlankCoord);
            byte[] goalTileLoc = goalInds[i][movedTile];
            int prevDist = (curBlankCoord[0] == goalTileLoc[0]
                         && curBlankCoord[1] == goalTileLoc[1]) ? 0 : 1;
            int curDist = (prevBlankCoord[0] == goalTileLoc[0])
                       && (prevBlankCoord[1] == goalTileLoc[1]) ? 0 : 1;

            out.add(prevHScores.get(i) + curDist - prevDist);
        }

        if (DEBUG_DELTA) {
            assert (out.equals(fullCompute(grid, goalInds)));
        }

        return out;
    }
}
