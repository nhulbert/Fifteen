package fringe;

import java.util.*;

import fifteen.Grid;
import fifteen.Node;

public class AStarArrQueue implements Fringe {    
    private List<AStarNode> data;
    private List<Grid> goals;
    private byte[][][] goalInds;
    
    public AStarArrQueue() {
        data = new ArrayList<>();
    }
    
    @Override
    public void add(Grid grid, Node parent) {
        AStarNode aStarNode = new AStarNode(grid, parent);
        Integer gridIndex = data.indexOf(aStarNode);
        
        if (gridIndex == -1 || aStarNode.fScore < data.get(gridIndex).fScore) {
            if (gridIndex == -1) {
                gridIndex = data.size();
                data.add(null);
            }
            
            data.set(gridIndex, aStarNode);
            
            bubbleUp(gridIndex);
        }
    }

    @Override
    public void addAll(List<Grid> grids, Node parent) {
        for (Grid grid : grids) {
            add(grid, parent);
        }
    }

    @Override
    public Node next() {
        AStarNode out = data.get(0);
        
        AStarNode last = data.remove(data.size()-1);
        
        if (!data.isEmpty()) {
            
            data.set(0, last);
            bubbleDown();
        }
        
        return out;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void setGoals(List<Grid> goals) {
        this.goals = goals;
        goalInds = new byte[goals.size()][][];
        for (int i=0; i<goals.size(); i++) {
            goalInds[i] = goals.get(i).getTileIndexMap();
        }
    }
    
    private void bubbleDown() {
        int ind1 = 0;
        int ind2 = 1;
        
        while (ind2 < data.size()) {
            if (ind2+1 < data.size() && data.get(ind2+1).compareTo(data.get(ind2)) < 0) {
                ind2++;
            }
            if (data.get(ind2).compareTo(data.get(ind1)) >= 0) {
                break;
            }
            
            swapDataInds(ind1, ind2);
            
            ind1 = ind2;
            ind2 = 2*ind1+1;
        }
    }
    
    private void bubbleUp(int ind1) {
        int ind2 = (ind1-1)/2;
        
        while (ind1 != 0 && data.get(ind1).compareTo(data.get(ind2)) < 0) {
            swapDataInds(ind1, ind2);
            
            ind1 = ind2;
            ind2 = (ind1-1)/2;
        }
    }
    
    private void swapDataInds(int ind1, int ind2) {
        AStarNode temp = data.get(ind1);
        data.set(ind1, data.get(ind2));
        data.set(ind2, temp);
    }
    
    private class AStarNode extends Node implements Comparable<AStarNode> {
        int gScore;
        List<Integer> hScores;
        int fScore;
        
        public AStarNode(Grid grid, Node parent) {
            super(grid, parent);
            
            if (parent == null) {
                this.gScore = 0;
                
                hScores = new ArrayList<>(goals.size());
                for (byte[][] goalInd : goalInds) {
                    hScores.add(heuristic(grid, goalInd));
                }
            } else {
                AStarNode prev = (AStarNode)parent;
                gScore = prev.gScore+1;
                
                hScores = new ArrayList<>(goals.size());
                for (int i=0; i<goals.size(); i++) {                
                    int[] curBlankCoord = grid.getBlankLocation();
                    int[] prevBlankCoord = prev.getGrid().getBlankLocation();
                    
                    int movedTile = grid.getTileAt(prevBlankCoord);
                    byte[] goalTileLoc = goalInds[i][movedTile];
                    int prevManhattanDist = Math.abs(curBlankCoord[0] - goalTileLoc[0]) +
                                            Math.abs(curBlankCoord[1] - goalTileLoc[1]);
                    int curManhattanDist = Math.abs(prevBlankCoord[0] - goalTileLoc[0]) +
                                           Math.abs(prevBlankCoord[1] - goalTileLoc[1]);
                    
                    hScores.add(prev.hScores.get(i) + curManhattanDist - prevManhattanDist);
                }
            }
            
            fScore = gScore + Collections.min(hScores);
        }

        @Override
        public int compareTo(AStarNode other) {
            return fScore - other.fScore;
        }
        
        private int heuristic(Grid cur, byte[][] goalInd) {
            int accum = 0;
            
            for (int i=0; i<Grid.NUM_TILES; i++) {
                int[] loc = {i%Grid.ROW_SIZE, i/Grid.ROW_SIZE};
                int tile = cur.getTileAt(loc);
                if (tile != 0) { // 0 is the blank tile
                    byte[] goalLoc = goalInd[tile];
                    
                    accum += Math.abs(goalLoc[0] - loc[0]) + Math.abs(goalLoc[1] - loc[1]);
                }
            }
            
            return accum;
        }
    }
}
