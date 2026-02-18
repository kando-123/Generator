package pl.polsl.generator.edge;

import java.util.Arrays;
import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Edge
{
    private final Vertex start;
    private final Vertex end;
    private final int travelTimes[];

    Edge(Vertex start, Vertex end, int travelTimes[])
    {
        if (travelTimes == null)
        {
            throw new NullPointerException("Parameter travelTimes shall be non-null.");
        }
        if (Arrays.stream(travelTimes).anyMatch(i -> i < 0))
        {
            throw new IllegalArgumentException("Travel times shall be nonnegative.");
        }
        this.start = start;
        this.end = end;
        this.travelTimes = Arrays.copyOf(travelTimes, travelTimes.length);
    }

    public Vertex getStart()
    {
        return start;
    }

    public Vertex getEnd()
    {
        return end;
    }
    
    public int getTravelTime(int intervalIndex)
    {
        return travelTimes[intervalIndex];
    }
    
    public int getIntervalCount()
    {
        return travelTimes.length;
    }
}
