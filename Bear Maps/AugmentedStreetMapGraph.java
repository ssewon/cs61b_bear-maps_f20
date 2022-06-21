package bearmaps.proj2d;

import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.*;
import edu.princeton.cs.algs4.TrieSET;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private List<Point> points;
    private Map<Point, Node> pointToNode;
    private Map<String, List<Node>> nameToNodes;
    private TrieSET trieSet;
    private HashMap<String, HashSet<String>> full;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        points = new ArrayList<>();
        pointToNode = new HashMap<>();
        nameToNodes = new HashMap<>();
        trieSet = new TrieSET();
        full = new HashMap<>();
        for (Node node : nodes) {
            if (name(node.id()) != null) {
                String cleanName = cleanString(name(node.id()));
                trieSet.add(cleanName);
                if (!nameToNodes.containsKey(cleanName)) {
                    nameToNodes.put(cleanName, new LinkedList<>());
                }
                nameToNodes.get(cleanName).add(node);
            }

            if (neighbors(node.id()).size() > 0) {
                Point point = new Point(node.lon(), node.lat());
                points.add(point);
                pointToNode.put(point, node);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        PointSet pointSet = new WeirdPointSet(points);
        Point nearest = pointSet.nearest(lon, lat);
        Node nearestNode = pointToNode.get(nearest);
        return nearestNode.id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> returnedResults = new LinkedList<>();
        for (String name : trieSet.keysWithPrefix(cleanString(prefix))) {
            HashSet<String> temp = full.get(name);
            if (temp != null) {
                for (String f : temp) {
                    returnedResults.add(f);
                }
            }
        }
        return returnedResults;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> returning = new LinkedList<>();
        String cleanName = cleanString(locationName);
        if (nameToNodes.containsKey(cleanName)) {
            for (Node node: nameToNodes.get(cleanName)) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("lon", node.lon());
                temp.put("lat", node.lat());
                temp.put("name", node.name());
                temp.put("id", node.id());
                returning.add(temp);
            }
        }
        return returning;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }
}
