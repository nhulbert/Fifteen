/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fifteen;

/**
 * Represents a node in the Fifteen Puzzle solution search and includes
 * the grid state as well as a parent pointer.
 * @author Neil Hulbert
 * @version 1.0
 */
public class Node {
    /**
     * The state of the puzzle grid.
     */
    private Grid grid;

    /**
     * The parent Node to this Node.
     */
    private Node parent;

    /**
     * The constructor for a Node.
     * @param grid the grid state of the Node to be constructed.
     * @param parent the parent Node of the Node to be constructed.
     */
    public Node(final Grid grid, final Node parent) {
        this.grid = grid;
        this.parent = parent;
    }

    /**
     * Gets the parent Node to this node.
     * @return the parent Node to this node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Gets the Grid representing the grid state of this node.
     * @return the Grid representing the grid state of this node.
     */
    public Grid getGrid() {
        return grid;
    }
}
