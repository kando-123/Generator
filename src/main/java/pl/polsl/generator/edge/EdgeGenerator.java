package pl.polsl.generator.edge;

import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public interface EdgeGenerator
{
    Edge[] generateEdges(Vertex[] vertices);
}
