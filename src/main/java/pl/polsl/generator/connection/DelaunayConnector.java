package pl.polsl.generator.connection;

import java.util.*;
import pl.polsl.generator.point.*;
import pl.polsl.generator.triangle.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class DelaunayConnector implements Connector
{
    private final double minAngleInDegrees;

    public DelaunayConnector(double minAngleInDegrees)
    {
        this.minAngleInDegrees = minAngleInDegrees;
    }

    @Override
    public Connection[] connect(Point[] points)
    {
        Map<Point, Integer> indexation = new HashMap<>();
        for (int i = 0; i < points.length; ++i)
        {
            indexation.put(points[i], i);
        }

        DelaunayTriangulation delaunay = new DelaunayTriangulation();
        Triangle triangles[] = delaunay.triangulate(points);

        Set<Connection> connections = new HashSet<>();
        int[] incidenceCounter = new int[points.length];
        for (Triangle triangle : triangles)
        {
            Point corners[] = triangle.getPoints();
            int indexes[] = Arrays.stream(corners)
                    .mapToInt(indexation::get)
                    .toArray();
            assert indexes.length == 3;
            for (int i = 0; i < 3; ++i)
            {
                int first = indexes[i];
                int last = indexes[Triangle.next(i)];
                if (connections.add(new Connection(first, last)))
                {
                    ++incidenceCounter[first];
                    ++incidenceCounter[last];
                }
            }
        }
        
        Set<Connection> removables = new HashSet<>();
        for (Triangle triangle : triangles)
        {
            if (triangle.getMinimalAngleInDegrees() <= minAngleInDegrees)
            {
                int opposite = triangle.getMaximalAngleIndex();
                int left = Triangle.next(opposite);
                int right = Triangle.prev(opposite);
                Point[] corners = triangle.getPoints();
                int first = indexation.get(corners[left]);
                int last = indexation.get(corners[right]);
                if (incidenceCounter[first] > 1 && incidenceCounter[last] > 1)
                {
                    if (removables.add(new Connection(first, last)))
                    {
                        --incidenceCounter[first];
                        --incidenceCounter[last];
                    }
                }
            }
        }
        connections.removeAll(removables);
        
        return connections.toArray(Connection[]::new);
    }
}
