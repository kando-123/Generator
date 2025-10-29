package pl.polsl.generator.triangle;

import java.util.*;
import pl.polsl.generator.point.Point;

/**
 *
 * @author Kay Jay O'Nail
 */
public class DelaunayTriangulation implements Triangulation
{
    @Override
    public UnorderedIntPair[] triangulate(Point[] points)
    {
        List<UnorderedIntPair> edges = new ArrayList<>();
        
        Point[] pointsSortedByX = Arrays.stream(points)
                .sorted((o1, o2) -> Double.compare(o1.x, o2.x))
                .toArray(Point[]::new);
    }
}
