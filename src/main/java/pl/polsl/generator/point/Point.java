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

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double length()
    {
        return Math.hypot(x, y);
    }
    
    public double lengthSquared()
    {
        return x * x + y * y;
    }
    
    public double distance(Point that)
    {
        return Math.hypot(this.x - that.x, this.y - that.y);
    }
    
    public Point plus(Point that)
    {
        return new Point(this.x + that.x, this.y + that.y);
    }
    
    public Point minus(Point that)
    {
        return new Point(this.x - that.x, this.y - that.y);
    }
    
    public Point times(double scalar)
    {
        return new Point(scalar * x, scalar * y);
    }
    
    public Point average(Point that)
    {
        return new Point((this.x + that.x) / 2, (this.y + that.y) / 2);
    }
    
    public double dot(Point that)
    {
        return this.x * that.x + this.y * that.y;
    }
    
    public double cross(Point that)
    {
        return this.x * that.y - this.y * that.x;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Point that
               && Math.round(1_000_000 * this.x) == Math.round(1_000_000 * that.x)
               && Math.round(1_000_000 * this.y) == Math.round(1_000_000 * that.y);
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
        return String.format(Locale.US, "(%.6f, %.6f)", x, y);
    }
}
