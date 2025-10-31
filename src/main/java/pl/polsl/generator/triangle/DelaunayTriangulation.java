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
    public Triangle[] triangulate(Point[] points)
    {
        List<Coordinate> coordinates = Arrays.stream(points)
                .map(p -> new Coordinate(p.x, p.y))
                .toList();
        var delaunay = new DelaunayTriangulationBuilder();
        delaunay.setSites(coordinates);
        Geometry triangles = delaunay.getTriangles(new GeometryFactory());
        int triangleCount = triangles.getNumGeometries();
        var result = new Triangle[triangleCount];
        for (int i = 0; i < triangleCount; ++i)
        {
            var polygon = (Polygon) triangles.getGeometryN(i);
            var coords = polygon.getCoordinates();
            var p1 = new Point(coords[0].x, coords[0].y);
            var p2 = new Point(coords[1].x, coords[1].y);
            var p3 = new Point(coords[2].x, coords[2].y);
            result[i] = new Triangle(p1, p2, p3);
        }
        
        return result;
    }
}
