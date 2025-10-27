package pl.polsl.generator.graph;

import pl.polsl.generator.demand.*;
import pl.polsl.generator.point.*;
import pl.polsl.generator.time.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class GraphGenerator
{
    private final PointGenerator pointGen;
    private final DemandGenerator demandGen;
    private final ServiceTimeFunction serviceTimeFun;

    public GraphGenerator(PointGenerator pointGen, DemandGenerator demandGen, ServiceTimeFunction serviceTimeFun)
    {
        this.pointGen = pointGen;
        this.demandGen = demandGen;
        this.serviceTimeFun = serviceTimeFun;
    }
    
}
