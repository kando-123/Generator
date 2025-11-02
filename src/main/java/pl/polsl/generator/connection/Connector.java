package pl.polsl.generator.connection;

import pl.polsl.generator.point.Point;

/**
 *
 * @author Kay Jay O'Nail
 */
public interface Connector
{
    Connection[] connect(Point[] vertices);
}
