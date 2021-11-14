package lab11.graphs;

import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    Maze maze;
    int s;
    int t;
    ArrayDeque<Integer> dq;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        dq = new ArrayDeque<Integer>();
        dq.add(s);
        marked[s] = true;
        announce();
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        dq.remove(s);
        for (int w : maze.adj(s)) {
            if (!marked[w]) {
                marked[w] = true;
                distTo[w] = distTo[s] + 1;
                edgeTo[w] = s;
                if (w == t) {
                    return;
                }
                dq.add(w);
                announce();
            }
        }
        while (!dq.isEmpty()) {
            int v = dq.remove();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    if (w == t) {
                        return;
                    }
                    dq.add(w);
                    announce();
                }
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}