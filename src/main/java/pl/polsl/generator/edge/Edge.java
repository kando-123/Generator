package pl.polsl.generator.edge;

import pl.polsl.generator.vertex.Vertex;

/**
 * 
 * @author Kay Jay O'Nail
 */
public class Edge
{
    private final Vertex end;
    private final int[] travelTime;

    public Edge(Vertex end, int intervalCount)
    {
        this.end = end;
        this.travelTime = new int[intervalCount];
    }
}
