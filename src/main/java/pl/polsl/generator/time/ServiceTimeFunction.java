/*
 * 
 * 
 */
package pl.polsl.generator.time;

/**
 *
 * @author Kay Jay O'Nail
 */
@FunctionalInterface
public interface ServiceTimeFunction
{
    int serviceTime(double demand);
}
