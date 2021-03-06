import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Objects;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Comparator;



/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    Graph graph;

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        graph = new Graph();
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Set<Long> keySet = graph.nodes.keySet();
        keySet.removeIf(id -> graph.nodes.get(id).adj.isEmpty());
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return graph.nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return graph.nodes.get(v).adj;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double dist = Double.POSITIVE_INFINITY;
        long minID = 0;
        for (long id : graph.nodes.keySet()) {
            if (distance(graph.nodes.get(id).lon, graph.nodes.get(id).lat, lon, lat) < dist) {
                dist = distance(graph.nodes.get(id).lon, graph.nodes.get(id).lat, lon, lat);
                minID = id;
            }
        }
        return minID;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return graph.nodes.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return graph.nodes.get(v).lat;
    }

    public static class Node {
        double lon;
        double lat;
        long id;
        double distTo; // Encapsulate it into instance variable make it accessible easily
        double heuristic;
        long nodeTo;
        String name;
        Set<Long> adj; // Adjacent nodes cannot be duplicate so we use set
        String way;


        public Node(double lon, double lat, long id) {
            this.lon = lon;
            this.lat = lat;
            this.id = id;
            this.distTo = Double.POSITIVE_INFINITY;
            this.heuristic = Double.POSITIVE_INFINITY;
            this.nodeTo = -1;
            this.name = null;
            this.adj = new HashSet<>();
            this.way = "";
        }

        @Override
        public boolean equals(Object that) {
            if (this.getClass() != that.getClass()) {
                return false;
            } else {
                return this.id == ((Node) that).id;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(lon, lat, id);
        }
    }

    public static class Edge {
        Node from;
        Node to;
        double weight;

        public Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
            this.weight = distance(from.lon, from.lat, to.lon, to.lat);
            from.adj.add(to.id);
        }

        @Override
        public boolean equals(Object that) {
            if (this.getClass() != that.getClass()) {
                return false;
            } else {
                return this.from.equals(((Edge) that).from) && this.to.equals(((Edge) that).to);
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, weight);
        }
    }

    public static class Graph {
        Map<Long, Node> nodes; // Reflect IDs of nodes to themselves
        Set<Edge> edges;
        Set<Way> ways;

        public Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>(); // No need for sorting these edges so pick a hashset
            ways = new HashSet<>();
        }
    }

    public static class DistToComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return (int) Math.signum((o1.distTo + o1.heuristic) - (o2.distTo + o2.heuristic));
        }
    }

    public static class Way {
        Map<Long, Node> nodes;
        Set<Edge> edges;
        long id;
        String maxSpeed;
        String name;

        public Way() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
            id = 0;
            maxSpeed = null;
            name = null;
        }
    }
}
