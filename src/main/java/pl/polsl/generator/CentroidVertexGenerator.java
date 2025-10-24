package pl.polsl.generator;

/**
 *
 * @author Kay Jay O'Nail
 */
public class CentroidVertexGenerator
{
    private final Centroid[] centroids;
    
    protected static final class Centroid
    {
        double x;
        double y;
        
        Centroid(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
        
        Centroid()
        {
            this(0, 0);
        }
    }

    public CentroidVertexGenerator(int[] counts, double[] radii)
    {
        int count = 0;
        for (int c : counts)
        {
            if (c <= 0)
            {
                throw new IllegalArgumentException("Negative centroid count.");
            }
            count += c;
        }
        centroids = new Centroid[count];
    }
}
