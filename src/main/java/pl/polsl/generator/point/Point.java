package pl.polsl.generator.point;

import java.util.Locale;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Point
{
    public final double x;
    public final double y;

    Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof Point that ? this.x == that.x && this.y == that.y : false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
    
    @Override
    public String toString()
    {
        return String.format(Locale.US, "(%.3f, %.3f)", x, y);
    }
}
