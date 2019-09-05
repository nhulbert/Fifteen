/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import fifteen.Grid;
import fifteen.Node;

/**
 * Defines a Fringe that queues Nodes for an BFS graph search.
 * @author Neil Hulbert
 * @version 1.0
 */
public class FIFOFringe implements Fringe {
    /**
     * The Queue that stores the queued Nodes.
     */
    private Queue<Node> data;

    /**
     * The set of Grids that have already been explored.
     */
    private Set<Grid> visited;

    /**
     * The constructor for a new FIFOFringe.
     */
    public FIFOFringe() {
        data = new LinkedList<>();
        visited = new HashSet<>();
    }

    @Override
    public void add(final Grid grid, final Node parent) {
        if (!visited.contains(grid)) {
            data.add(new Node(grid, parent));
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
        Node out =  data.remove();
        visited.add(out.getGrid());
        return out;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void setGoals(final List<Grid> nodes) {
        // is an uninformed search, has no effect
    }
}
