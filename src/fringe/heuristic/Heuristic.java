/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe.heuristic;

import java.util.List;

import fifteen.Grid;

/**
 * An interface that defines the API for a search heuristic to be used to
 * estimate the distance from a Grid to a goal, given a map of the goal grid's
 * tiles to their respective coordinates.
 * @author Neil Hulbert
 * @version 1.0
 *
 */
public interface Heuristic {
    /**
     * Calculates the heuristic distances from a Grid to one or more goals.
     * @param grid the grid from which a goal distance estimate will be found.
     * @param goalInds a map between each goal's tile to the tile's coordinates,
     * such that goalInds[i][h][j] yields the jth coordinate of the hth tile of
     * the ith goal.
     * @return a list of the estimated distances from grid to each of the goals.
     */
    List<Integer> fullCompute(Grid grid, byte[][][] goalInds);

    /**
     * Calculates the heuristic distances from a Grid to one or more goals given
     * the same heuristic's estimated distances from a prior Grid from which the
     * current Grid is move adjacent.
     * @param grid the grid from which a goal distance estimate will be found.
     * @param goalInds a map between each goal's tile to the tile's coordinates,
     * such that goalInds[i][h][j] yields the jth coordinate of the hth tile of
     * the ith goal.
     * @param prevHScores the previously computed heuristic distances for a
     * prior Grid from which grid is move adjacent.
     * @param prevBlankCoord the coordinates of the blank tile location in the
     * prior Grid
     * @return a list of the estimated distances from grid to each of the goals.
     */
    List<Integer> deltaCompute(Grid grid,
                               byte[][][] goalInds,
                               List<Integer> prevHScores,
                               int[] prevBlankCoord);
}
