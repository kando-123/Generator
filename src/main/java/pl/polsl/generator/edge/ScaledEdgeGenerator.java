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
        
        /* Speed in [m/s] */
        double speeds[] = getSpeeds(cumulatedDistances);
        
        throw new UnsupportedOperationException("Under development.");
    }
    
    protected double[] getSpeeds(double distances[])
    {
        double maximum = distances[0];
        double minimum = distances[0];
        
        double average = distances[0];
        
        for (int i = 1; i < distances.length; ++i)
        {
            double distance = distances[i];
            if (distance > maximum)
            {
                maximum = distance;
            }
            else if (distance < minimum)
            {
                minimum = distance;
            }
            average += distance;
        }
        average /= distances.length;
        double deviation = 0;
        for (double distance : distances)
        {
            double difference = distance - average;
            deviation += difference * difference;
        }
        deviation = Math.sqrt(deviation);
        
        /* GENERALIZE */
        
        double lower = minimum < average - deviation ? average - deviation : (minimum + average) / 2;
        double higher = maximum > average + deviation ? average + deviation : (maximum + average) / 2;
        
        double thresholds[] = new double[] { minimum, lower, higher, maximum };
        double speeds[] = new double[] { 0, 30/3.6, 50/3.6, 70/3.6, 0 };
        
        var convertion = new ThresholdBasedDistanceToSpeedConvertion(thresholds, speeds);
        
    }
}
