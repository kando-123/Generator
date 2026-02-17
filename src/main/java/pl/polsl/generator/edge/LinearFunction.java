package pl.polsl.generator.edge;

/**
 *
 * @author Kay Jay O'Nail
 */
public class LinearFunction
{
    private final double a;
    private final double b;
    
    public LinearFunction(double a, double b)
    {
        this.a = a;
        this.b = b;
    }
    
    public LinearFunction(double xMin, double xMax, double yMin, double yMax)
    {
        if (xMin == xMax)
        {
            throw new IllegalArgumentException("Empty input range.");
        }
        a = (yMax - yMin) / (xMax - xMin);
        b = yMin - a * xMin;
    }
    
    public double slope()
    {
        return a;
    }
    
    public double inercept()
    {
        return b;
    }
    
    public double of(double x)
    {
        return a * x + b;
    }
}
