package pl.polsl.generator.time;

import java.util.Random;
import static java.lang.Math.min;

/**
 *
 * @author Kay Jay O'Nail
 */
public class BilinearServiceTimeFunction implements ServiceTimeFunction
{
    private final double lowerSlope;
    private final double upperSlope;
    private final Random random;

    public BilinearServiceTimeFunction(double lowerSlope, double upperSlope)
    {
        if (!(lowerSlope < upperSlope && lowerSlope > 0))
        {
            throw new IllegalArgumentException("Lower and upper slope have to be positive, and the lower slope shall be less than the upper slope.");
        }
        this.lowerSlope = lowerSlope;
        this.upperSlope = upperSlope;
        random = new Random();
    }

    public BilinearServiceTimeFunction(double lowerSlope, double upperSlope, long seed)
    {
        if (!(lowerSlope < upperSlope && lowerSlope > 0))
        {
            throw new IllegalArgumentException("Lower and upper slope have to be positive, and the lower slope shall be less than the upper slope.");
        }
        this.lowerSlope = lowerSlope;
        this.upperSlope = upperSlope;
        random = new Random(seed);
    }
    
    @Override
    public int serviceTime(double demand)
    {
        if (demand < 0)
        {
            throw new IllegalArgumentException("Negative demand.");
        }
        else if (demand == 0)
        {
            return 0;
        }
        else
        {
            double lowerBound = lowerSlope * demand;
            double upperBound = upperSlope * demand;
            return (int) random.nextDouble(lowerBound, upperBound);
        }
    }
}
