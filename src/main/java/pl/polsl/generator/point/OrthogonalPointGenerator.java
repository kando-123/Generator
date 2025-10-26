package pl.polsl.generator.point;

import java.util.*;

/**
 *
 * @author Kay Jay O'Nail
 */
public class OrthogonalPointGenerator implements PointGenerator
{
    private final int width;
    private final int height;
    private final Random random;

    public OrthogonalPointGenerator(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.random = new Random();
    }

    @Override
    public Point[] generate(int n)
    {
        Set<Point> points = new HashSet<>();
        while (points.size() < n)
        {
            double x = random.nextDouble(width);
            double y = random.nextDouble(height);
            points.add(new Point(x, y));
        }
        return points.stream().toArray(Point[]::new);
    }
}
