package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean hasCircle = false;
    private boolean[] onStack = new boolean[maze.V()];

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        edgeTo[0] = 0;
        findCycle(0);
    }

    // Helper methods go here
    private void findCycle(int v) {
        marked[v] = true;
        onStack[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                findCycle(w);
            }
            if (onStack[w] && edgeTo[v] != w) {
                hasCircle = true;
                return;
            }
        }
        onStack[v] = false;
        announce();
    }
}