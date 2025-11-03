package pl.polsl.generator.edge;

import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public class EdgePrototype
{
    private final Vertex start;
    private final Vertex end;
    private final int travelTimes[];

    EdgePrototype(Vertex start, Vertex end, int intervalCount)
    {
        this.start = start;
        this.end = end;
        this.travelTimes = new int[intervalCount];
    }
    
    void setTravelTime(int index, int value)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException("The travel time shall be nonnegative.");
        }
        travelTimes[index] = value;
    }
    
    private Double length = null;
    
    double getEuclideanLength()
    {
        if (length == null)
        {
            length = Math.hypot(start.getX() - end.getX(), start.getY() - end.getY());
        }
        return length;
    }
    
    double getTimeForSpeed(double speed)
    {
        return getEuclideanLength() / speed;
    }
    
    Edge getEdge()
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
