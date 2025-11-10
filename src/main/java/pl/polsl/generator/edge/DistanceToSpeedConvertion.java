package pl.polsl.generator.edge;

/**
 *
 * @author Kay Jay O'Nail
 */
@FunctionalInterface
public interface DistanceToSpeedConvertion
{
    double speed(double distance);
}
