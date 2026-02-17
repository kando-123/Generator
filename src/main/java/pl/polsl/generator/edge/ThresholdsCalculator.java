package pl.polsl.generator.edge;

/**
 *
 * @author Kay Jay O'Nail
 */
public interface ThresholdsCalculator
{
    double[] calculteThresholds(double[] values);
    
    int getThresholdsCount();
}
