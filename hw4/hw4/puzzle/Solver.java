package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    MinPQ<SearchNode> pq;
    Queue<WorldState> solution;
    SearchNode goal;
    int counter = 0;

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        solution = new Queue<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        pq.insert(initialNode);
        counter++;
        goal = search(initialNode);
    }

    private SearchNode search(SearchNode min) {
        if (min.ws.isGoal()) {
            solution.enqueue(pq.delMin().ws);
            return min;
        }

        solution.enqueue(pq.delMin().ws);

        for (WorldState neighbor : min.ws.neighbors()) {
            if (!(min.prevNode == null) && neighbor.equals(min.prevNode.ws)) {
                continue;
            }
            SearchNode neighborNode = new SearchNode(neighbor, min.prevCount + 1, min);
            pq.insert(neighborNode);
            counter++;
        }
        System.out.println("Now pq has " + pq.size() + " items");

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
            countToGoal = state.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.prevCount + this.countToGoal > that.prevCount + that.countToGoal) {
                return 1;
            } else if (this.prevCount + this.countToGoal == that.prevCount + that.countToGoal) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
