package pl.polsl.generator.time;

import java.util.Random;

/**
 *
 * @author Kay Jay O'Nail
 */
public class BilinearServiceTimeFunction implements ServiceTimeFunction
{
    private final double lowerSlope;
    private final double upperSlope;
    private final Random random = new Random();

    public BilinearServiceTimeFunction(double lowerSlope, double upperSlope)
    {
        if (lowerSlope < upperSlope)
        {
            this.lowerSlope = lowerSlope;
            this.upperSlope = upperSlope;
        }
        else if (lowerSlope > upperSlope)
        {
            this.lowerSlope = upperSlope;
            this.upperSlope = lowerSlope;
        }
        else
        {
            throw new IllegalArgumentException("Lower and upper slope have to be different.");
        }
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
