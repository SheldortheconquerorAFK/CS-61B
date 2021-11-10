package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    MinPQ<SearchNode> pq;
    Queue<WorldState> solution;
    SearchNode goal;
    //int counter = 0;

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        solution = new Queue<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        pq.insert(initialNode);
        //counter++;
        search();
    }

    private void search() {
        SearchNode next = pq.delMin();
        if (next.worldState.isGoal()) {
            goal = next;
            solution.enqueue(goal.worldState);
            return;
        }

        solution.enqueue(next.worldState);
        //System.out.println(solution.size());

        for (WorldState neighbor : next.worldState.neighbors()) {
            if (next.prevNode != null && neighbor.equals(next.prevNode.worldState)) {
                continue;
            }
            SearchNode nextNeighbor = new SearchNode(neighbor, next.prevCount + 1, next);
            pq.insert(nextNeighbor);
            //counter++;
        }

        search();
    }

    public int moves() {
        return goal.prevCount;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        WorldState worldState;
        int prevCount;
        SearchNode prevNode;
        int distToGoal;

        private SearchNode(WorldState worldState, int prevCount, SearchNode prevNode) {
            this.worldState = worldState;
            this.prevCount = prevCount;
            this.prevNode = prevNode;
            this.distToGoal = worldState.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.prevCount + this.distToGoal > that.prevCount + that.distToGoal) {
                return 1;
            } else if (this.prevCount + this.distToGoal == that.prevCount + that.distToGoal) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}