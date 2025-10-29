package pl.polsl.generator.triangle;

import java.util.Arrays;
import java.util.List;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import pl.polsl.generator.point.Point;

/**
 * Delaunay triangulation algorithm.
 *
 * @author Kay Jay O'Nail
 */
public class DelaunayTriangulation implements Triangulation
{
    @Override
    public IntPair[] triangulate(Point[] points)
    {
        List<Coordinate> coordinates = Arrays.stream(points)
                .map(p -> new Coordinate(p.x, p.y))
                .toList();
        
        DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
        builder.setSites(coordinates);
        
        Geometry triangles = builder.getTriangles(new GeometryFactory());
        
        System.out.println(triangles);
        
        throw new UnsupportedOperationException("Under development.");
    }
}
