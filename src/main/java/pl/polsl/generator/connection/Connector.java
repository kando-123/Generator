package pl.polsl.generator.connection;

import pl.polsl.generator.point.Point;

/**
 *
 * @author Kay Jay O'Nail
 */
public interface Connector
{
    /**
     * Connects the {@code points} in an implementation-defined way (preferably
     * a triangulation method). The connections in the returned array <em>shall</em>
     * be made of the indices the points originally have in the {@code points} array.
     * @param points array of the points to triangulate
     * @return connections between the points
     */
    Connection[] connect(Point[] points);
}
