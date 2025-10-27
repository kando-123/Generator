package pl.polsl.generator.vertex;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Vertex
{
    private final int i;
    private final double x;
    private final double y;
    private final double q;
    private final int t;
    private final Map<String, int[]> e;
    
    private Vertex(int i, double x, double y, double q, int t)
    {
        this.i = i;
        this.x = x;
        this.y = y;
        this.q = q;
        this.t = t;
        this.e = new LinkedHashMap<>();
    }
    
    public static Vertex createDepot(int i, double x, double y)
    {
        return new Vertex(i, x, y, 0, 0);
    }
    
    public static Vertex createDepot(double x, double y)
    {
        return createDepot(0, x, y);
    }
    
    public static Vertex createCustomer(int i, double x, double y, double q, int t)
    {
        return q > 0 && t >= 0 ? new Vertex(i, x, y, q, t) : null;
    }
    
    public int getIndex()
    {
        return i;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getDemand()
    {
        return q;
    }
    
    public int getServiceTime()
    {
        return t;
    }
}
