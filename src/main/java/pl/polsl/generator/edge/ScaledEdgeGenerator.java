package pl.polsl.generator.edge;

import java.util.*;
import pl.polsl.generator.connection.Connection;
import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public class ScaledEdgeGenerator implements EdgeGenerator
{
    private final double speedFraction[];

    public ScaledEdgeGenerator(double speedFraction[])
    {
        if (Arrays.stream(speedFraction).anyMatch(i -> i <= 0 || i > 1))
        {
            throw new IllegalArgumentException("Speed fractions shall be in range from 0 (excl.) to 1 (incl.).");
        }
        this.speedFraction = speedFraction;
    }

    private int intervalCount()
    {
        return speedFraction.length;
    }

    @Override
    public Edge[] generateEdges(Vertex vertices[], Connection connections[])
    {
        // For every connected vertices, emit two edges prototypes.
        // Assess the maximal speed on the edge.
        // Calculate the travel times.
        // Emit the edges from the prototypes.

        List<Set<EdgePrototype>> prototypes = new ArrayList<>();
        final int intervalCount = intervalCount();
        for (Connection connection : connections)
        {
            int first = connection.first;
            int last = connection.last;
            if (prototypes.get(first) == null)
            {
                prototypes.set(first, new HashSet<>());
            }
            if (prototypes.get(last) == null)
            {
                prototypes.set(last, new HashSet<>());
            }
            Vertex start = vertices[first];
            Vertex end = vertices[last];
            prototypes.get(first).add(new EdgePrototype(start, end, intervalCount));
            prototypes.get(last).add(new EdgePrototype(end, start, intervalCount));
        }
        
        double cumulatedDistances[] = new double[vertices.length];
        for (int i = 0; i < prototypes.size(); ++i)
        {
            cumulatedDistances[i] = prototypes.get(i).stream()
                    .mapToDouble(EdgePrototype::getEuclideanLength)
                    .sum();
        }
        
        double speeds[] = new double[prototypes.size()];
        for (int i = 0; i < speeds.length; ++i)
        {
            
        }
    }
}
