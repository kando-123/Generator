/*
 * 
 * 
 */
package pl.polsl.generator.vertex;

import pl.polsl.generator.point.Point;

/**
 *
 * @author Kay Jay O'Nail
 */
@FunctionalInterface
public interface VertexGenerator
{
    Vertex[] generateVertices(Point[] points);    
}
