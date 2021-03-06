import java.util.Objects;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        PriorityQueue<GraphDB.Node> pq = new PriorityQueue<>(new GraphDB.DistToComparator());
        Set<Long> visited = new HashSet<>();
        Stack<Long> stack = new Stack<>();
        ArrayList<Long> path = new ArrayList<>();
        long st = g.closest(stlon, stlat);
        long dest = g.closest(destlon, destlat);
        GraphDB.Node stNode = g.graph.nodes.get(st);

        stNode.distTo = 0;
        stNode.heuristic = g.distance(st, dest);
        stNode.nodeTo = 0;

        pq.add(stNode);
        visited.add(st);

        while (!pq.isEmpty()) {
            GraphDB.Node next = pq.poll();
            if (next.id == dest) {
                break;
            }
            relax(pq, g, next, dest, visited);
        }


        for (GraphDB.Node current = g.graph.nodes.get(dest);
             current.id != st;
             current = g.graph.nodes.get(current.nodeTo)) {
            stack.push(current.id);
        }
        stack.push(st);

        while (!stack.isEmpty()) {
            path.add(stack.pop());
        }

        for (long i : visited) {
            g.graph.nodes.get(i).distTo = Double.POSITIVE_INFINITY;
            g.graph.nodes.get(i).heuristic = Double.POSITIVE_INFINITY;
            g.graph.nodes.get(i).nodeTo = -1;
        }

        return path;
    }

    private static void relax(PriorityQueue<GraphDB.Node> pq,
                              GraphDB g,
                              GraphDB.Node next,
                              long dest,
                              Set<Long> visited) {
        for (long adjID : next.adj) {
            GraphDB.Node adjNode = g.graph.nodes.get(adjID);
            if (adjNode.distTo > next.distTo + g.distance(next.id, adjID)) {
                adjNode.distTo = next.distTo + g.distance(next.id, adjID);
                adjNode.heuristic = g.distance(adjID, dest);
                adjNode.nodeTo = next.id;
                visited.add(adjID);
                pq.add(adjNode);
            }
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigationDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        ArrayList<NavigationDirection> list = new ArrayList<>();
        long curr = 0;
        long prev = route.remove(0);
        GraphDB.Node currNode = null;
        GraphDB.Node prevNode = g.graph.nodes.get(prev);
        NavigationDirection startND = new NavigationDirection();
        try {
            startND.direction = NavigationDirection.START;
            startND.way = prevNode.way;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        NavigationDirection currND = startND;

        double dist = 0;

        while (!route.isEmpty()) {
            curr = route.remove(0);
            currNode = g.graph.nodes.get(curr);
            if (!prevNode.way.equals(currNode.way)) {
                currND.distance = dist + g.distance(prev, curr);
                list.add(currND);
                dist = 0;
                currND = new NavigationDirection();
                currND.direction = bearingIndex(g, prev, curr);
                currND.way = currNode.way;
            } else {
                dist += g.distance(prev, curr);
            }
            prev = curr;
            prevNode = currNode;
        }
        return list;
    }

    private static int bearingIndex(GraphDB g, long v, long w) {
        if (g.bearing(v, w) >= -15 && g.bearing(v, w) <= 15) {
            return NavigationDirection.STRAIGHT;
        } else if (g.bearing(v, w) >= -30) {
            return NavigationDirection.SLIGHT_LEFT;
        } else if (g.bearing(v, w) <= 30) {
            return NavigationDirection.SLIGHT_RIGHT;
        } else if (g.bearing(v, w) <= 100) {
            return NavigationDirection.RIGHT;
        } else if (g.bearing(v, w) >= -100) {
            return NavigationDirection.LEFT;
        } else if (g.bearing(v, w) < -100) {
            return NavigationDirection.SHARP_LEFT;
        } else {
            return NavigationDirection.SHARP_RIGHT;
        }
    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
