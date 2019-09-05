/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fringe;

import java.util.List;
import fifteen.Grid;
import fifteen.Node;

/**
 * Defines the API for a Fringe to be used for a Fifteen Puzzle solution search.
 * @author Neil Hulbert
 * @version 1.0
 */
public interface Fringe {
    /**
     * Creates a Node from the provided Grid and parent Node and adds it to the
     * queue of Nodes to be explored.
     * @param grid the grid state of the Node to be explored.
     * @param parent the parent of the Node to be explored.
     */
    void add(Grid grid, Node parent);

    /**
     * Creates Nodes from the provided List of Grids and common parent Node and
     * adds them to the queue of Nodes to be explored.
     * @param grids the respective grid states of the Nodes to be explored.
     * @param parent the common parent of the Nodes to be explored.
     */
    void addAll(List<Grid> grids, Node parent);

    /**
     * Removes the next Node to be explored from the queue and returns it.
     * @return the next Node to be explored.
     */
    Node next();

    /**
     * Returns the number of Nodes in the queue.
     * @return the number of Nodes in the queue.
     */
    int size();

    /**
     * Sets the Grids that are goal states to be used by the Fringe in an
     * informed search.
     * @param goals the goal states to be used by the Fringe in an informed
     * search.
     */
    void setGoals(List<Grid> goals);
}
