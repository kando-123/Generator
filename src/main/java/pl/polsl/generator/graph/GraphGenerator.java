package pl.polsl.generator.graph;

import java.util.Arrays;
import pl.polsl.generator.demand.*;
import pl.polsl.generator.edge.Edge;
import pl.polsl.generator.edge.EdgeGenerator;
import pl.polsl.generator.point.*;
import pl.polsl.generator.time.*;
import pl.polsl.generator.vertex.Vertex;

/**
 *
 * @author Kay Jay O'Nail
 */
public class GraphGenerator
{
    private final PointGenerator pointGenerator;
    private final DemandGenerator demandGenerator;
    private final ServiceTimeFunction serviceTimeFunction;
    private final EdgeGenerator edgeGenerator;
    
    private final int vertexCount;
    private final double totalDemand;
    private final double demandUnit;
    
    private final int startTime;
    private final int interval;
    private final int intervalCount;

    private GraphGenerator(GraphGeneratorBuilder builder)
    {
        this.pointGenerator = builder.pointGenerator;
        this.demandGenerator = builder.demandGenerator;
        this.serviceTimeFunction = builder.serviceTimeFunction;
        this.edgeGenerator = builder.edgeGenerator;
        
        this.vertexCount = builder.vertexCount;
        this.totalDemand = builder.totalDemand;
        this.demandUnit = builder.demandUnit;
        
        this.startTime = builder.startTime;
        this.interval = builder.interval;
        this.intervalCount = builder.intervalCount;
    }
    
    public Graph generateGraph()
    {
        /* Generate the points */
        Point[] points = pointGenerator.generatePoints(vertexCount);
        
        /* Generate the demand for all points except the depot */
        double[] demand = demandGenerator.generateDemand(vertexCount - 1, totalDemand, demandUnit);
        
        /* Map the demand to service time */
        int[] serviceTime = Arrays.stream(demand)
                .mapToInt(serviceTimeFunction::serviceTime)
                .toArray();
        
        /* Create vertices */
        Vertex[] vertices = new Vertex[vertexCount];
        vertices[0] = Vertex.createDepot(points[0]);
        for (int i = 1; i < vertexCount; ++i)
        {
            vertices[i] = Vertex.createCustomer(i, points[i], demand[i - 1], serviceTime[i - 1]);
        }
        
        /* Create the edges */
        Edge[] edges = edgeGenerator.generateEdges(vertices);
        
        
        
        throw new UnsupportedOperationException("Under development.");
    }
    
    public static class GraphGeneratorBuilder
    {
        private PointGenerator pointGenerator;
        private DemandGenerator demandGenerator;
        private ServiceTimeFunction serviceTimeFunction;
        private EdgeGenerator edgeGenerator;
        
        private Integer vertexCount;
        private Double totalDemand;
        private Double demandUnit;
        
        private Integer startTime;
        private Integer interval;
        private Integer intervalCount;
        
        public GraphGeneratorBuilder setPointGenerator(PointGenerator pointGenerator)
        {
            if (pointGenerator == null)
            {
                throw new NullPointerException("Parameter pointGenerator shall not be null.");
            }
            this.pointGenerator = pointGenerator;
            return this;
        }
        
        public GraphGeneratorBuilder setDemandGenerator(DemandGenerator demandGenerator)
        {
            if (demandGenerator == null)
            {
                throw new NullPointerException("Parameter demandGenerator shall not be null.");
            }
            this.demandGenerator = demandGenerator;
            return this;
        }
        
        public GraphGeneratorBuilder setServiceTimeFunction(ServiceTimeFunction serviceTimeFunction)
        {
            if (serviceTimeFunction == null)
            {
                throw new NullPointerException("Parameter serviceTimeFunction shall not be null.");
            }
            this.serviceTimeFunction = serviceTimeFunction;
            return this;
        }
        
        public GraphGeneratorBuilder setEdgeGenerator(EdgeGenerator edgeGenerator)
        {
            if (edgeGenerator == null)
            {
                throw new NullPointerException("Parameter edgeGenerator shall not be null.");
            }
            this.edgeGenerator = edgeGenerator;
            return this;
        }
        
        public GraphGeneratorBuilder setVertexCount(int vertexCount)
        {
            if (vertexCount <= 1)
            {
                throw new IllegalArgumentException("Parameter vertexCount shall be greater than 1.");
            }
            this.vertexCount = vertexCount;
            return this;
        }
        
        public GraphGeneratorBuilder setTotalDemand(double totalDemand)
        {
            if (totalDemand <= 0)
            {
                throw new IllegalArgumentException("Parameter totalDemand shall be positive.");
            }
            this.totalDemand = totalDemand;
            return this;
        }
        
        public GraphGeneratorBuilder setDemandUnit(double demandUnit)
        {
            if (demandUnit <= 0)
            {
                throw new IllegalArgumentException("Parameter demandUnit shall be positive.");
            }
            this.demandUnit = demandUnit;
            return this;
        }
        
        public GraphGeneratorBuilder setStartTime(int startTime)
        {
            if (startTime <= 0)
            {
                throw new IllegalArgumentException("Parameter startTime shall be positive.");
            }
            this.startTime = startTime;
            return this;
        }
        
        public GraphGeneratorBuilder setInterval(int interval)
        {
            if (interval <= 0)
            {
                throw new IllegalArgumentException("Parameter interval shall be positive.");
            }
            this.interval = interval;
            return this;
        }
        
        public GraphGeneratorBuilder setIntervalCount(int intervalCount)
        {
            if (intervalCount <= 0)
            {
                throw new IllegalArgumentException("Parameter intervalCount shall be positive.");
            }
            this.intervalCount = intervalCount;
            return this;
        }
        
        public GraphGenerator build()
        {
            if (demandUnit > totalDemand)
            {
                throw new IllegalStateException("Value demandUnit shall be less than totalDemand.");
            }
            return new GraphGenerator(this);
        }
    }
}
