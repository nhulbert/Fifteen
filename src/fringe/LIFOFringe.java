/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import fifteen.Grid;
import fifteen.Node;

/**
 * Defines a Fringe that queues Nodes for an BFS graph search.
 * @author Neil Hulbert
 * @version 1.0
 */
public class LIFOFringe implements Fringe {
    /**
     * The List that stores the queued Nodes.
     */
    protected List<Node> data;

    /**
     * The set of Grids that have already been explored.
     */
    protected Set<Grid> visited;

    /**
     * The constructor for a new LIFOFringe.
     */
    public LIFOFringe() {
        data = new ArrayList<>();
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
        // add in reverse order to preserve correct move priority
        ListIterator<Grid> it = grids.listIterator(grids.size());

        while (it.hasPrevious()) {
            add(it.previous(), parent);
        }
    }

    @Override
    public Node next() {
        Node out = data.remove(data.size() - 1);
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
