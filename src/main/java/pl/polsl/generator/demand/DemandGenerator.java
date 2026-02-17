/*
 * 
 * 
 */
package pl.polsl.generator.demand;

/**
 *
 * @author Kay Jay O'Nail
 */
public interface DemandGenerator
{
    double[] generateDemand(int count, double total, double unit);
}
