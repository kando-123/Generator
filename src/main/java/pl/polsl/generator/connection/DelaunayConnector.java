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
                    .mapToInt(p -> indexation.get(p))
                    .toArray();
            assert indexes.length == 3;
            for (int i = 0; i < 3; ++i)
            {
                int first = indexes[i];
                int last = indexes[(i + 1) % 3];
                Connection connection = new Connection(first, last);
                if (!connections.contains(connection))
                {
                    ++incidenceCounter[first];
                    ++incidenceCounter[last];
                    connections.add(connection);
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
                if (incidenceCounter[left] > 1 && incidenceCounter[right] > 1)
                {
                    removables.add(new Connection(left, right));
                    --incidenceCounter[left];
                    --incidenceCounter[right];
                }
            }
        }
        connections.removeAll(removables);
        
        return connections.toArray(Connection[]::new);
    }
}
