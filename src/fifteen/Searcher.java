/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fifteen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import fringe.Fringe;

/**
 * Performs the search for a solution to the Fifteen Puzzle.
 * @author Neil Hulbert
 * @version 1.0
 */
public class Searcher {
    /**
     * The list of possible moves the blank location can make within the
     * puzzle.
     */
    public static final int[][] DIRS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};


    /**
     * The Fringe object holding the queue of nodes to be explored.
     */
    private Fringe fringe;

    /**
     * The constructor for a new Searcher object.
     * @param fringe the fringe object to be used to queue the nodes to be
     * explored.
     */
    public Searcher(final Fringe fringe) {
        this.fringe = fringe;
    }

    /**
     * Performs the search for a solution to the Fifteen Puzzle.
     * @param start the grid state from which to start the search.
     * @param goals the goal states that represent a solved puzzle.
     * @return a list of each grid state the puzzle with take on the found
     * path from the start grid state to one of the goal grid states, in order
     */
    public List<Grid> search(final Grid start, final List<Grid> goals) {
        fringe.setGoals(goals);
        fringe.add(start, null);

        int numCreated = 1;
        int expanded = 0;
        int maxFringe = 0;

        while (fringe.size() != 0) {
            Node current = fringe.next();

            if (goals.contains(current.getGrid())) {
                List<Grid> solution = retraceSolution(current);
                System.out.print(solution.size() - 1);
                System.out.print(", " + numCreated);
                System.out.print(", " + expanded);
                System.out.println(", " + maxFringe);
                return solution;
            }

            expanded++;

            List<Grid> newNodes = new ArrayList<>(DIRS.length);

            for (int[] dir : DIRS) {
                Grid newGrid = current.getGrid().move(dir);
                if (newGrid != null) {
                    newNodes.add(newGrid);
                }
            }

            int prevSize = fringe.size();
            fringe.addAll(newNodes, current);
            int curSize = fringe.size();
            if (curSize > maxFringe) {
                maxFringe = curSize;
            }
            numCreated += curSize - prevSize;
        }

        System.out.println("-1, 0, 0, 0");

        return null;
    }

    /**
     * Follows the chain of parents starting with a given Node to the first
     * Node with no parent in order to retrace a solution path for the Fifteen
     * Puzzle.
     * @param current The final Node in the desired path.
     * @return a list of each grid state the puzzle with take on the found
     * path from the start grid state to one of the goal grid states, in order
     */
    private List<Grid> retraceSolution(Node current) {
        LinkedList<Grid> out = new LinkedList<>();

        while (current != null) {
            out.addFirst(current.getGrid());
            current = current.getParent();
        }

        return out;
    }
}
