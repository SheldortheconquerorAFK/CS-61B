package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {
    MinPQ<SearchNode> pq;
    SearchNode goal;
    Queue<WorldState> solution;

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        solution = new Queue<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        pq.insert(initialNode);
        goal = search(initialNode);

    }

    private SearchNode search(SearchNode sn) {
        if (sn.ws.isGoal()) {
            return sn;
        }
        solution.enqueue(pq.delMin().ws);
        for (WorldState neighbor : sn.ws.neighbors()) {
            if (neighbor.equals(sn.prevNode.ws)) {
                continue;
            }
            SearchNode neighborNode = new SearchNode(neighbor, sn.prevCount + 1, sn);
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
