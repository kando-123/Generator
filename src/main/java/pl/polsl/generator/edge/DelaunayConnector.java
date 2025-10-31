package pl.polsl.generator.edge;

import java.util.*;
import pl.polsl.generator.point.*;
import pl.polsl.generator.triangle.*;
import pl.polsl.generator.triangle.*;
import pl.polsl.generator.vertex.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class DelaunayConnector implements Connector
{
    private final Double minAngleInDegrees;

    public DelaunayConnector(double minAngleInDegrees)
    {
        this.minAngleInDegrees = minAngleInDegrees;
    }

    public DelaunayConnector()
    {
        this.minAngleInDegrees = null;
    }

    @Override
    public IntPair[] connect(Vertex[] vertices)
    {
        Point points[] = new Point[vertices.length];

        Map<Point, Integer> indexation = new HashMap<>();
        for (int i = 0; i < points.length; ++i)
        {
            indexation.put(points[i], i);
        }

        DelaunayTriangulation delaunay = new DelaunayTriangulation();
        Triangle triangles[] = delaunay.triangulate(points);

        Set<IntPair> connections = new HashSet<>();
        int[] incidenceCounter = new int[points.length];
        for (Triangle triangle : triangles)
        {
            Point corners[] = triangle.getPoints();
            for (int i = 0; i < 3; ++i)
            {
                int first = indexation.get(corners[i]);
                int last = indexation.get(corners[i < 2 ? i + 1 : 0]);
                ++incidenceCounter[first];
                ++incidenceCounter[last];
                connections.add(new IntPair(first, last));
            }
        }

        return connections.toArray(IntPair[]::new);
    }
}
