package pl.polsl.generator.edge;

import java.util.Arrays;
import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public class EdgePrototype
{
    private final Vertex start;
    private final Vertex end;
    private final double length;
    private final double[] travelSpeeds;

    EdgePrototype(Vertex start, Vertex end, int intervalCount)
    {
        this.start = start;
        this.end = end;
        this.length = Math.hypot(start.getX() - end.getX(), start.getY() - end.getY());
        this.travelSpeeds = new double[intervalCount];
    }
    
    Vertex getStart()
    {
        return start;
    }
    
    Vertex getEnd()
    {
        return end;
    }
    
    double getLength()
    {
        return length;
    }
    
    void setSpeed(int index, double value)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException("The travel time shall be nonnegative.");
        }
        travelSpeeds[index] = value;
    }
    
    int getTime(double speed)
    {
        return (int) (length / speed);
    }
    
    Edge getEdge()
    {
        int[] travelTimes = Arrays.stream(travelSpeeds)
                .mapToInt(this::getTime)
                .toArray();
        return new Edge(start, end, travelTimes);
    }
}
