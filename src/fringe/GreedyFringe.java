/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe;

import fringe.heuristic.Heuristic;
import java.util.List;
import java.util.Set;

import fifteen.Grid;
import fifteen.Node;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Defines a Fringe that queues Nodes for a greedy best first search.
 * @author Neil Hulbert
 * @version 1.0
 */
public class GreedyFringe implements Fringe {
    /**
     * The queue holding the next Node to be explored.
     */
    private Node next;

    /**
     * The heuristic based on which the next best Node will be chosen.
     */
    private Heuristic heuristic;

    /**
     * Holds the coordinates for each of each goal's tiles.
     */
    private byte[][][] goalInds;

    /**
     * The set of Grids that have already been explored.
     */
    private Set<Grid> visited;

    /**
     * Constructs a new GreedyFringe using the provided heuristic.
     * @param heuristic the heuristic by which the greedy best first search
     * is to be guided.
     */
    public GreedyFringe(final Heuristic heuristic) {
        this.heuristic = heuristic;
        visited = new HashSet<>();
    }

    @Override
    public void add(final Grid grid, final Node parent) {
        next = new GreedyNode(grid, parent);
    }

    @Override
    public void addAll(final List<Grid> grids, final Node parent) {
        grids.stream().map(grid -> new GreedyNode(grid, parent))
                      .min(Comparator.naturalOrder())
                      .ifPresentOrElse(greedyNode -> next = greedyNode,
                                       () -> next = null);
    }

    @Override
    public Node next() {
        Node out = next;
        visited.add(out.getGrid());
        next = null;
        return out;
    }

    @Override
    public int size() {
        return (next == null || visited.contains(next.getGrid())) ? 0 : 1;
    }

    @Override
    public void setGoals(final List<Grid> goals) {
        goalInds = new byte[goals.size()][][];
        for (int i = 0; i < goals.size(); i++) {
            goalInds[i] = goals.get(i).getTileIndexMap();
        }
    }

    /**
     * Defines a Node object extended to include heuristic distances to each
     * goal.
     * @author Neil Hulbert
     * @version 1.0
     */
    private class GreedyNode extends Node implements Comparable<GreedyNode> {
        /**
         * The estimated distances to each goal, defined by the heuristic.
         */
        private List<Integer> hScores;

        /**
         * The constructor for an AStarNode which initializes grid and parent
         * with the provided pointers and calculates and stores the heuristic
         * distance to each goal from the Node.
         * @param grid the state of the grid at this Node
         * @param parent the parent Node to this Node
         */
        GreedyNode(final Grid grid, final Node parent) {
            super(grid, parent);

            if (parent == null) {
                hScores = heuristic.fullCompute(grid, goalInds);
            } else {
                GreedyNode prev = (GreedyNode) parent;
                hScores =
                    heuristic.deltaCompute(grid,
                                           goalInds,
                                           prev.hScores,
                                           prev.getGrid().getBlankLocation());
            }
        }

        @Override
        public int compareTo(final GreedyNode other) {
            return Collections.min(hScores) - Collections.min(other.hScores);
        }
    }
}
