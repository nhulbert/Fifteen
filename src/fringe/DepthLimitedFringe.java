/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe;

import java.util.ArrayList;
import java.util.List;
import fifteen.Grid;
import fifteen.Node;

/**
 * Defines a Fringe that queues Nodes for a depth limited search.
 * @author Neil Hulbert
 * @version 1.0
 */
public class DepthLimitedFringe implements Fringe {
    /**
     * The List that stores the queued nodes.
     */
    protected List<Node> data;

    /**
     * The maximum depth at which to search.
     */
    protected int maxDepth;

    /**
     * Constructs a new DepthLimitedFringe given the provided maximum depth.
     * @param maxDepth the maximum depth at which to search.
     */
    public DepthLimitedFringe(final int maxDepth) {
        data = new ArrayList<>();
        this.maxDepth = maxDepth;
    }

    @Override
    public void add(final Grid grid, final Node parent) {
        DepthNode idnParent = (DepthNode) parent;
        int depth = (idnParent == null) ? 0 : idnParent.depth + 1;

        if (depth <= maxDepth) {
            data.add(new DepthNode(grid, idnParent));
        }
    }

    @Override
    public void addAll(final List<Grid> grids, final Node parent) {
        DepthNode idnParent = (DepthNode) parent;
        int depth = (idnParent == null) ? 0 : idnParent.depth + 1;

        for (Grid grid : grids) {
            if (depth <= maxDepth) {
                data.add(new DepthNode(grid, idnParent));
            }
        }
    }

    @Override
    public Node next() {
        return data.remove(data.size() - 1);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void setGoals(final List<Grid> goals) {
        // is an uninformed search, has no effect
    }

    /**
     * @author Neil Hulbert
     * @version 1.0
     */
    private class DepthNode extends Node {
        /**
         * The search depth at which this Node will be expanded.
         */
        private int depth;

        /**
         * The constructor for a DepthNode which initializes grid and parent
         * with the provided pointers and stores the depth for the Node.
         * @param grid the state of the grid at this Node
         * @param parent the parent Node to this Node
         */
        DepthNode(final Grid grid, final DepthNode parent) {
            super(grid, parent);

            depth = (parent == null) ? 0 : parent.depth + 1;
        }
    }
}
