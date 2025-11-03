package pl.polsl.generator.edge;

import java.util.Arrays;
import pl.polsl.generator.connection.Connection;
import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public class ScaledEdgeGenerator implements EdgeGenerator
{
    private final double speedFraction[];

    public ScaledEdgeGenerator(double[] speedFraction)
    {
        if (Arrays.stream(speedFraction).anyMatch(i -> i <= 0))
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
    public Edge[] generateEdges(Vertex[] vertices, Connection[] connections)
    {
        
    }
}
