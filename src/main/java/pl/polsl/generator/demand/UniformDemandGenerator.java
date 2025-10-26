package pl.polsl.generator.demand;

import java.util.Random;

/**
 *
 * @author Kay Jay O'Nail
 */
public class UniformDemandGenerator implements DemandGenerator
{
    @Override
    public double[] generateDemand(int number, double total, double unit)
    {
        if (number <= 0 || total <= 0 || unit <= 0)
        {
            throw new IllegalArgumentException("Nonpositive number, total demand or unit.");
        }
        if (number * unit > total)
        {
            throw new IllegalArgumentException("Total demand insufficient for given number of units.");
        }
        
        double[] allocation = new double[number];
        for (int i = 0; i < number; ++i)
        {
            allocation[i] = unit;
        }
        double sum = number * unit;
        Random random = new Random();
        while (sum < total)
        {
            int index = random.nextInt(number);
            allocation[index] += unit;
            sum += unit;
        }
        
        return allocation;
    }
}
