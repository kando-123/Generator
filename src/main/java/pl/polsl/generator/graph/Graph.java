package pl.polsl.generator.graph;

import java.util.*;
import com.google.gson.*;
import pl.polsl.generator.edge.Edge;
import pl.polsl.generator.vertex.Vertex;

class Node
{
    final Vertex vertex;
    final List<Edge> edges;

    Node(Vertex vertex, List<Edge> edges)
    {
        this.vertex = vertex;
        this.edges = edges;
    }
    
    private static final String KEY_X_COORDINATE = "x";
    private static final String KEY_Y_COORDINATE = "y";
    private static final String KEY_DEMAND = "q";
    private static final String KEY_SERVICE_TIME = "t";
    private static final String KEY_EDGES = "e";
    
    JsonElement emit()
    {
        JsonObject nodeObject = new JsonObject();
        
        nodeObject.addProperty(KEY_X_COORDINATE, vertex.getX());
        nodeObject.addProperty(KEY_Y_COORDINATE, vertex.getY());
        nodeObject.addProperty(KEY_DEMAND, vertex.getDemand());
        nodeObject.addProperty(KEY_SERVICE_TIME, vertex.getServiceTime());
        
        JsonObject edgesObject = new JsonObject();
        edges.sort((Edge e1, Edge e2) -> Integer.compare(e1.getEnd().getIndex(), e2.getEnd().getIndex()));
        for (Edge edge : edges)
        {
            assert edge.getStart().getIndex() == vertex.getIndex(): "Node has an edge that begins in another vertex.";
            
            JsonArray times = new JsonArray(edge.getIntervalCount());
            for (int i = 0; i < edge.getIntervalCount(); ++i)
            {
                times.add(edge.getTravelTime(i));
            }
            String end = String.valueOf(edge.getEnd().getIndex());
            edgesObject.add(end, times);
        }
        nodeObject.add(KEY_EDGES, edgesObject);
        
        return nodeObject;
    }
}

/**
 *
 * @author Kay Jay O'Nail
 */
public class Graph
{
    private final List<Node> nodes;
    
    Graph()
    {
        nodes = new ArrayList<>();
    }

    void addNode(Vertex vertex, List<Edge> edges)
    {
        nodes.add(new Node(vertex, edges));
    }
    
    /**
     * Returns a JSON object representing the vertices and edges of the graph.
     * The fields of the object are indexes of the vertices (successive 0-based integers
     * in form of strings) mapped to vertex data objects. Vertex data are its coordinates
     * ("x", "y"), the demand ("demand") and the edges ("edges"). All edges are defined
     * in the "edges" property begin in the current vertex. An edge is a key-value pair
     * of the "edges" object. The key is the index of the end vertex; the value is an array
     * of travel times.
     */
    public JsonElement toJson()
    {
        JsonObject object = new JsonObject();
        for (Node node : nodes)
        {
            String index = String.valueOf(node.vertex.getIndex());
            JsonElement nodeElement = node.emit();
            object.add(index, nodeElement);
        }
        return object;
    }
}
