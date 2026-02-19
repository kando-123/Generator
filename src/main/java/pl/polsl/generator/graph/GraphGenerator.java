package pl.polsl.generator.graph;

import java.util.Arrays;
import java.util.List;

import pl.polsl.generator.connection.*;
import pl.polsl.generator.demand.*;
import pl.polsl.generator.edge.Edge;
import pl.polsl.generator.edge.EdgeGenerator;
import pl.polsl.generator.point.*;
import pl.polsl.generator.time.*;
import pl.polsl.generator.vertex.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class GraphGenerator
{
    private final PointGenerator pointGenerator;
    private final DemandGenerator demandGenerator;
    private final ServiceTimeFunction serviceTimeFunction;
    private final Connector connector;
    private final EdgeGenerator edgeGenerator;
    
    private final int vertexCount;
    private final double totalDemand;
    private final double demandUnit;

    private GraphGenerator(GraphGeneratorBuilder builder)
    {
        pointGenerator = builder.pointGenerator;
        demandGenerator = builder.demandGenerator;
        serviceTimeFunction = builder.serviceTimeFunction;
        connector = builder.connector;
        edgeGenerator = builder.edgeGenerator;
        
        vertexCount = builder.vertexCount;
        totalDemand = builder.totalDemand;
        demandUnit = builder.demandUnit;
    }
    
    public Graph generateGraph()
    {
        /* Generate the points */
        Point[] points = pointGenerator.generatePoints(vertexCount);
        
        /* Generate the demand for all points except the depot */
        double[] demand = demandGenerator.generateDemand(vertexCount - 1, totalDemand, demandUnit);
        
        /* Map the demand to service time */
        int serviceTime[] = Arrays.stream(demand)
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
        Connection connections[] = connector.connect(points);
        
        List<List<Edge>> edges = edgeGenerator.generateEdges(vertices, connections);
        
        Graph graph = new Graph();
        
        for (int i = 0; i < vertexCount; ++i)
        {
            graph.addNode(vertices[i], edges.get(i));
        }
        
        return graph;
    }
    
    public static class GraphGeneratorBuilder
    {
        private PointGenerator pointGenerator;
        private DemandGenerator demandGenerator;
        private ServiceTimeFunction serviceTimeFunction;
        private Connector connector;
        private EdgeGenerator edgeGenerator;
        
        private Integer vertexCount;
        private Double totalDemand;
        private Double demandUnit;
        
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
        
        public GraphGeneratorBuilder setConnector(Connector connector)
        {
            if (connector == null)
            {
                throw new NullPointerException("Parameter connector shall not be null.");
            }
            this.connector = connector;
            return this;
        }

        public GraphGeneratorBuilder setEdgeGenerator(EdgeGenerator edgeGenerator)
        {
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
