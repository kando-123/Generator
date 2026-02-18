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
    /**
     * The table of fractions of the maximal speed that the speed in given interval of the day consitutes.
     */
    private final double[] speedFractions;
    private final double minSpeed;
    private final double maxSpeed;
    
    public ScaledEdgeGenerator(double speedFraction[], double minSpeed, double maxSpeed)
    {
        if (Arrays.stream(speedFraction).anyMatch(i -> i <= 0 || i > 1))
        {
            throw new IllegalArgumentException("Speed fractions shall be in range from 0 (excl.) to 1 (incl.).");
        }
        this.speedFractions = speedFraction;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    private int intervalCount()
    {
        return speedFractions.length;
    }

    @Override
    public List<List<Edge>> generateEdges(Vertex[] vertices, Connection[] connections)
    {
        EdgePrototype[] prototypes = new EdgePrototype[2 * connections.length];
        
        double minLength = +Double.MAX_VALUE;
        double maxLength = -Double.MAX_VALUE;
        
        int p = 0;
        for (Connection connection : connections)
        {
            Vertex start = vertices[connection.first];
            Vertex end = vertices[connection.last];
            
            EdgePrototype prototype = new EdgePrototype(start, end, intervalCount());
            double length = prototype.getLength();
            if (length > maxLength)
            {
                maxLength = length;
            }
            else if (length < minLength)
            {
                minLength = length;
            }
            prototypes[p++] = prototype;
            
            prototype = new EdgePrototype(end, start, intervalCount());
            length = prototype.getLength();
            if (length > maxLength)
            {
                maxLength = length;
            }
            else if (length < minLength)
            {
                minLength = length;
            }
            prototypes[p++] = prototype;
        }

        LinearFunction function = new LinearFunction(minLength, maxLength, minSpeed, maxSpeed);
        List<List<Edge>> edges = new ArrayList<>(vertices.length);
        for (int i = 0; i < vertices.length; ++i)
        {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < prototypes.length; ++i)
        {
            EdgePrototype prototype = prototypes[i];
            double length = prototype.getLength();
            double speed = function.of(length);
            for (int t = 0; t < intervalCount(); ++t)
            {
                prototype.setSpeed(t, speedFractions[t] * speed);
            }
            Edge edge = prototype.getEdge();
            int index = edge.getStart().getIndex();
            edges.get(index).add(edge);
        }
        return edges;
    }
}
