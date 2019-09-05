/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe;

import fifteen.Node;

/**
 * Defines a Fringe that queues Nodes for an Iterative Deepening search.
 * @author Neil Hulbert
 * @version 1.0
 */
public class IterativeDeepeningFringe extends DepthLimitedFringe {
    /**
     * Constructs a new IterativeDeepeningFringe.
     */
    public IterativeDeepeningFringe() {
        super(0);
    }

    @Override
    public Node next() {
        if (data.size() == 1) {
            maxDepth++;
            return data.get(0);
        } else {
            return data.remove(data.size() - 1);
        }
    }
}
