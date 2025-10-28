package pl.polsl.generator.vertex;

import com.google.gson.*;
import pl.polsl.generator.point.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Vertex implements Comparable<Vertex>
{
    private final int index;
    private final Point point;
    private final double demand;
    private final int serviceTime;

    Vertex(int index, Point point, double demand, int serviceTime)
    {
        this.index = index;
        this.point = point;
        this.demand = demand;
        this.serviceTime = serviceTime;
    }
    
    public static Vertex createDepot(Point point)
    {
        return new Vertex(0, point, 0, 0);
    }
    
    public static Vertex createCustomer(int index, Point point, double demand, int serviceTime)
    {
        if (index > 0 && demand > 0 && serviceTime >= 0)
        {
            return new Vertex(index, point, demand, serviceTime);
        }
        else
        {
            throw new IllegalArgumentException("Invalid data of a customer: ");
        }
    }
    
    public int getIndex()
    {
        return index;
    }
    
    public double getX()
    {
        return point.x;
    }
    
    public double getY()
    {
        return point.y;
    }
    
    public double getDemand()
    {
        return demand;
    }
    
    public int getServiceTime()
    {
        return serviceTime;
    }
    
    @Override
    public String toString()
    {
        return String.format("Vertex{i: %d, x: %f, y: %f, q: %f, t: %d}",
                index,
                point.x,
                point.y,
                demand,
                serviceTime);
    }
    
    public JsonElement toJsonElement()
    {
        JsonObject object = new JsonObject();
        object.addProperty("i", index);
        object.addProperty("x", point.x);
        object.addProperty("y", point.y);
        object.addProperty("q", demand);
        object.addProperty("t", serviceTime);
        return object;
    }

    @Override
    public int compareTo(Vertex that)
    {
        return Integer.compare(this.index, that.index);
    }
}
