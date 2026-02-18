package pl.polsl.generator.edge;

import java.util.List;
import pl.polsl.generator.connection.Connection;
import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public interface EdgeGenerator
{
    List<List<Edge>> generateEdges(Vertex[] vertices, Connection[] connections);
}
