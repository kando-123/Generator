package pl.polsl.generator.edge;

public class AverageBasedThresholdsCalculator implements ThresholdsCalculator
{
    private final static int THRESHOLDS_COUNT = 5;
    
    @Override
    public double[] calculteThresholds(double[] values)
    {
        double maximum = values[0];
        double minimum = values[0];

        double sum = values[0];

        for (int i = 1; i < values.length; ++i)
        {
            double distance = values[i];
            if (distance > maximum)
            {
                maximum = distance;
            }
            else if (distance < minimum)
            {
                minimum = distance;
            }
            sum += distance;
        }
        double average = sum / values.length;
//        double variance = 0;
//        for (double distance : values)
//        {
//            double difference = distance - average;
//            variance += difference * difference;
//        }
//        double deviation = Math.sqrt(variance);

//        double lower = minimum < average - deviation
//                ? average - deviation
//                : (minimum + average) / 2;
//        
//        double higher = maximum > average + deviation
//                ? average + deviation
//                : (maximum + average) / 2;

        double lower = (minimum + average) / 2;
        double higher = (average + maximum) / 2;

        double[] thresholds = new double[]
        {
            minimum, lower, average, higher, maximum
        };
        assert 0 <= minimum && minimum < lower && lower < average && average < higher && higher < maximum:
                String.format("Order of thresholds happened to be invalid: [%f, %f, %f, %f, %f]", minimum, lower, average, higher, maximum);
        assert thresholds.length == THRESHOLDS_COUNT;
        return thresholds;
    }

    @Override
    public int getThresholdsCount()
    {
        return THRESHOLDS_COUNT;
    }
}
