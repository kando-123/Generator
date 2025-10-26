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
    double[] generateDemand(int number, double total, double unit);
}
