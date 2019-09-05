/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe;

import fringe.heuristic.Heuristic;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import fifteen.Grid;
import fifteen.Node;

/**
 * Defines a Fringe that queues Nodes for an A* graph search.
 * @author Neil Hulbert
 * @version 1.0
 */
public class AStarFringe implements Fringe {
    /**
     * The priority queue that stores the queued Nodes.
     */
    private PriorityQueue<AStarNode> data;

    /**
     * Holds the coordinates for each of each goal's tiles.
     */
    private byte[][][] goalInds;

    /**
     * The closed set for the A* search.
     */
    private Set<Grid> visited;

    /**
     * The heuristic by which the A* search is guided.
     */
    private Heuristic heuristic;

    /**
     * Constructs a new AStarFringe using the provided heuristic.
     * @param heuristic the heuristic by which the A* search is to be guided.
     */
    public AStarFringe(final Heuristic heuristic) {
        data = new PriorityQueue<>();
        visited = new TreeSet<>();
        this.heuristic = heuristic;
    }

    @Override
    public void add(final Grid grid, final Node parent) {
        if (!visited.contains(grid)) {
            data.add(new AStarNode(grid, parent));
        }
    }

    @Override
    public void addAll(final List<Grid> grids, final Node parent) {
        for (Grid grid : grids) {
            add(grid, parent);
        }
    }

    @Override
    public Node next() {
        AStarNode aStarNode = data.remove();
        visited.add(aStarNode.getGrid());
        return aStarNode;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void setGoals(final List<Grid> goals) {
        goalInds = new byte[goals.size()][][];
        for (int i = 0; i < goals.size(); i++) {
            goalInds[i] = goals.get(i).getTileIndexMap();
        }
    }

    /**
     * Defines a Node object extended to include the scores needed for an A*
     * search.
     * @author Neil Hulbert
     * @version 1.0
     */
    private class AStarNode extends Node implements Comparable<AStarNode> {
        /**
         * The accumulated cost along the path from the start Node to this Node.
         */
        private int gScore;

        /**
         * The estimated costs to each goal, calculated using the heuristic.
         */
        private List<Integer> hScores;

        /**
         * The estimated cost of the path including this Node going from the
         * start Node to the closest goal.
         */
        private int fScore;

        /**
         * The constructor for an AStarNode which initializes grid and parent
         * with the provided pointers and calculates each A* score for the Node.
         * @param grid the state of the grid at this Node
         * @param parent the parent Node to this Node
         */
        AStarNode(final Grid grid, final Node parent) {
            super(grid, parent);

            if (parent == null) {
                this.gScore = 0;

                hScores = heuristic.fullCompute(grid, goalInds);
            } else {
                AStarNode prev = (AStarNode) parent;
                gScore = prev.gScore + 1;

                hScores =
                    heuristic.deltaCompute(grid,
                                           goalInds,
                                           prev.hScores,
                                           prev.getGrid().getBlankLocation());
            }

            fScore = gScore + Collections.min(hScores);
        }

        @Override
        public int compareTo(final AStarNode other) {
            return fScore - other.fScore;
        }
    }
}
