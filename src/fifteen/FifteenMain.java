/*
 * TCSS 435 - Summer 2019
 * Assignment 1 - FifteenPuzzle
 * Neil Hulbert
 */

package fifteen;

import java.util.ArrayList;
import java.util.List;

import fringe.AStarFringe;
import fringe.DepthLimitedFringe;
import fringe.FIFOFringe;
import fringe.Fringe;
import fringe.GreedyFringe;
import fringe.IterativeDeepeningFringe;
import fringe.LIFOFringe;
import fringe.heuristic.H1Heuristic;
import fringe.heuristic.H2Heuristic;
import fringe.heuristic.Heuristic;

/**
 * The class that contains the entry point for the Fifteen Puzzle command line
 * program.
 * @author Neil Hulbert
 * @version 1.0
 */
public final class FifteenMain {

    /**
     * The constructor, should not be used.
     */
    private FifteenMain() {
    }

    /**
     * The entry point for the Fifteen Puzzle command line program.
     * @param args the arguments for the puzzle solution search: args[0] holds
     * the starting grid tiles in order from left to right, wrapping from top
     * to bottom, with tiles represented as 1-9 and A-F and the empty space as
     * a space (" ") character. args[1] specifies the search method and args[2]
     * the search method parameter if required.
     */
    public static void main(final String[] args) {
        if (args == null || args.length < 1) {
            System.out.print("Please provide the initial puzzle ");
            System.out.println("grid as the first argument.");
            return;
        }

        Grid start = new Grid(args[0]);

        Fringe fringe = parseCreateFringe(args);

        if (fringe != null) {
            List<Grid> goals = new ArrayList<>();

            // specifies the goal states for the search
            goals.add(new Grid("123456789ABCDEF "));
            goals.add(new Grid("123456789ABCDFE "));

            new Searcher(fringe).search(start, goals);
        }
    }

    /**
     * Generates the Fringe object to be used in the search based on the
     * provided command line arguments.
     * @param args the arguments for the puzzle solution search: args[0] holds
     * the starting grid tiles in order from left to right, wrapping from top
     * to bottom, with tiles represented as 1-9 and A-F and the empty space as
     * a space (" ") character. args[1] specifies the search method and args[2]
     * the search method parameter if required.
     * @return a Fringe object to be used in a Fifteen Puzzle solution search,
     * or a null value if the provided arguments are invalid.
     */
    private static Fringe parseCreateFringe(final String[] args) {
        Fringe fringe = null;

        switch (args[1].toLowerCase()) {
            case "bfs":
                fringe = new FIFOFringe();
                break;
            case "dfs":
                fringe = new LIFOFringe();
                break;
            case "dls":
                if (args.length < 2) {
                    System.out.println("No depth limit specified");
                } else {
                    try {
                        int depthLimit = Integer.parseInt(args[2]);
                        if (depthLimit < 0) {
                            throw new NumberFormatException();
                        }
                        fringe = new DepthLimitedFringe(depthLimit);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid depth limit");
                    }
                }

                break;
            case "id":
                fringe = new IterativeDeepeningFringe();
                break;
            case "gbfs":
                if (args.length < 3) {
                    System.out.println("No heuristic specified");
                } else {

                    Heuristic heuristic = null;
                    switch (args[2].toLowerCase()) {
                        case "h1":
                            heuristic = new H1Heuristic();
                            break;
                        case "h2":
                            heuristic = new H2Heuristic();
                            break;
                        default:
                            System.out.println("Invalid heuristic specified");
                    }

                    if (heuristic != null) {
                        fringe = new GreedyFringe(heuristic);
                    }
                }

                break;
            case "astar":
                if (args.length < 2) {
                    System.out.println("No heuristic specified");
                } else {

                    Heuristic heuristic = null;
                    switch (args[2].toLowerCase()) {
                        case "h1":
                            heuristic = new H1Heuristic();
                            break;
                        case "h2":
                            heuristic = new H2Heuristic();
                            break;
                        default:
                            System.out.println("Invalid heuristic specified");
                    }

                    if (heuristic != null) {
                        fringe = new AStarFringe(heuristic);
                    }
                }

                break;
            default:
                System.out.println("Invalid search method");
        }

        return fringe;
    }
}
