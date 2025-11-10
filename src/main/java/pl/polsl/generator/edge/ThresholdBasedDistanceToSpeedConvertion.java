package pl.polsl.generator.edge;

/**
 * Assigns the speed value depending on the interval the distance belongs to.
 * 
 * Stores the thesholds
 */
public class ThresholdBasedDistanceToSpeedConvertion implements DistanceToSpeedConvertion
{
    private final double thresholds[];
    private final double speeds[];

    public ThresholdBasedDistanceToSpeedConvertion(double[] thresholds, double[] speeds)
    {
        if (speeds.length != thresholds.length + 1)
        {
            throw new IllegalArgumentException("The number of speeds shall be greater by 1 than number of thresholds.");
        }
        this.thresholds = thresholds.clone();
        this.speeds = speeds.clone();
    }
    
    @Override
    public double speed(double distance)
    {
        int i = 0;
        while (i < thresholds.length)
        {
            if (distance < thresholds[i])
            {
                break;
            }
            ++i;
        }
        return speeds[i];
    }
}
