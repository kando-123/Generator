package pl.polsl.generator.triangle;

import pl.polsl.generator.point.Point;

/**
 *
 * @author Kay Jay O'Nail
 */
public interface Triangulation
{
    UnorderedIntPair[] triangulate(Point[] points);
}
