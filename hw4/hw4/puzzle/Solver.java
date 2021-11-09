package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    MinPQ<SearchNode> pq;
    Queue<WorldState> solution;
    SearchNode goal;

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        solution = new Queue<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        pq.insert(initialNode);
        goal = search(initialNode);
    }

    private SearchNode search(SearchNode min) {
        if (min.ws.isGoal()) {
            return min;
        }
        solution.enqueue(pq.delMin().ws);
        for (WorldState neighbor : min.ws.neighbors()) {
            if (neighbor.equals(min.prevNode.ws)) {
                continue;
            }
            SearchNode neighborNode = new SearchNode(neighbor, min.prevCount + 1, min);
            pq.insert(neighborNode);
        }
        return search(pq.min());
    }

    public int moves() {
        return goal.prevCount;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        WorldState ws;
        int prevCount;
        SearchNode prevNode;
        int countToGoal;

        private SearchNode(WorldState state, int pc, SearchNode pn) {
            ws = state;
            prevCount = pc;
            prevNode = pn;
            countToGoal = ws.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode that) {
            return that.prevCount + that.countToGoal - (this.prevCount + this.countToGoal);
        }
    }
}
