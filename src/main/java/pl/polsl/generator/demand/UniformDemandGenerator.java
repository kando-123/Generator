package pl.polsl.generator.demand;

import java.util.Random;

/**
 *
 * @author Kay Jay O'Nail
 */
public class UniformDemandGenerator implements DemandGenerator
{
    private final Random random;
    
    public UniformDemandGenerator()
    {
        random = new Random();
    }
    
    public UniformDemandGenerator(long seed)
    {
        random = new Random(seed);
    }
    
    @Override
    public double[] generateDemand(int count, double total, double unit)
    {
        if (count <= 0 || total <= 0 || unit <= 0)
        {
            throw new IllegalArgumentException("Nonpositive count, total demand or unit.");
        }
        if (count * unit > total)
        {
            throw new IllegalArgumentException("Total demand insufficient for given count of units.");
        }
        
        double[] allocation = new double[count];
        for (int i = 0; i < count; ++i)
        {
            allocation[i] = unit;
        }
        double sum = count * unit;
        while (sum < total)
        {
            int index = random.nextInt(count);
            allocation[index] += unit;
            sum += unit;
        }
        
        return allocation;
    }
}
