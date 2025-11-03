package pl.polsl.generator.graph;

import java.util.*;
import com.google.gson.*;
import pl.polsl.generator.edge.Edge;
import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Graph
{
    private final Vertex[] vertices;
    private final Map<Integer, Set<Edge>> edges;
    
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
        throw new UnsupportedOperationException();
    }
}
