package pl.polsl.generator.triangle;

import java.util.Arrays;
import pl.polsl.generator.point.Point;

/**
 *
 * @author Kay Jay O'Nail
 */
public class Triangle
{
    private final Point[] points;

    public Triangle(Point p1, Point p2, Point p3)
    {
        points = new Point[3];

        if (p1.equals(p2) || p2.equals(p3) || p3.equals(p1))
        {
            throw new IllegalArgumentException("The points shall be pairwise unequal.");
        }

        double angle = Math.atan2(p3.y - p1.y, p3.x - p1.x) - Math.atan2(p2.y - p1.y, p2.x - p1.x) + Math.TAU;
        angle %= Math.TAU;

        if (angle == 0 || angle == Math.PI)
        {
            throw new IllegalArgumentException("The points shall be noncollinear");
        }

        points[0] = p1;
        if (angle < Math.PI)
        {
            points[1] = p2;
            points[2] = p3;
        }
        else
        {
            points[1] = p3;
            points[2] = p2;
        }

        assert (Math.atan2(points[2].y - points[0].y, points[2].x - points[0].x) - Math.atan2(points[1].y - points[0].y, points[1].x - points[0].x) + Math.TAU) % Math.TAU < Math.PI
                && (Math.atan2(points[0].y - points[1].y, points[0].x - points[1].x) - Math.atan2(points[2].y - points[1].y, points[2].x - points[1].x) + Math.TAU) % Math.TAU < Math.PI
                && (Math.atan2(points[1].y - points[2].y, points[1].x - points[2].x) - Math.atan2(points[0].y - points[2].y, points[0].x - points[2].x) + Math.TAU) % Math.TAU < Math.PI :
                "Counterclockwisification failed.";
    }

    public Point[] getPoints()
    {
        return points.clone();
    }

    public double[] getSides()
    {
        double[] sides = new double[3];
        for (int i = 0; i < 3; ++i)
        {
            Point p = points[(i + 1) % 3];
            Point q = points[(i + 2) % 3];
            sides[i] = Math.hypot(p.x - q.x, p.y - q.y);
        }
        return sides;
    }

    public double[] getAnglesInRadians()
    {
        double[] sides = getSides();
        double[] angles = new double[3];
        for (int i = 0; i < 3; ++i)
        {
            double a = sides[i];
            double b = sides[(i + 1) % 3];
            double c = sides[(i + 2) % 3];
            angles[i] = Math.acos((b * b + c * c - a * a) / (2 * b * c));
        }
        return angles;
    }

    public double[] getAnglesInDegrees()
    {
        return Arrays.stream(getAnglesInRadians())
                .map(Math::toDegrees)
                .toArray();
    }

    public double getMinimalAngleInRadians()
    {
        double[] angles = getAnglesInRadians();
        return Math.min(angles[0], Math.min(angles[1], angles[2]));
    }

    public double getMinimalAngleInDegrees()
    {
        return Math.toDegrees(getMinimalAngleInRadians());
    }

    public double getPerimeter()
    {
        double perimeter = 0;
        for (var side : getSides())
        {
            perimeter += side;
        }
        return perimeter;
    }

    public double getArea()
    {
        /* Heron's formula */
        double semiperimeter = 0.5 * getPerimeter();
        double area = semiperimeter;
        for (var side : getSides())
        {
            area *= semiperimeter - side;
        }
        return Math.sqrt(area);
    }

    /**
     * Computes the determinant of the matrix:
     * <table border="1">
     * <tr>
     * <td>x<sub>0</sub></td>
     * <td>y<sub>0</sub></td>
     * <td>x<sub>0</sub><sup>2</sup> + y<sub>0</sub><sup>2</sup></td>
     * <td>1</td>
     * </tr>
     * <tr>
     * <td>x<sub>1</sub></td>
     * <td>y<sub>1</sub></td>
     * <td>x<sub>1</sub><sup>2</sup> + y<sub>1</sub><sup>2</sup></td>
     * <td>1</td>
     * </tr>
     * <tr>
     * <td>x<sub>2</sub></td>
     * <td>y<sub>2</sub></td>
     * <td>x<sub>2</sub><sup>2</sup> + y<sub>2</sub><sup>2</sup></td>
     * <td>1</td>
     * </tr>
     * <tr>
     * <td>x</td>
     * <td>y</td>
     * <td>x<sup>2</sup> + y<sup>2</sup></td>
     * <td>1</td>
     * </tr>
     * </table>
     * and compares it against 0.
     * @param point the point that will be tested against membership to the circumcircle
     * @return true if and only if the point lies within the circumcircle of this triangle
     */
    public boolean liesInCircumcircle(Point point)
    {
        var entries = new double[16];
        for (int i = 0, e = 0; i < 3; ++i)
        {
            entries[e++] = points[i].x;
            entries[e++] = points[i].y;
            entries[e++] = points[i].lengthSquared();
            entries[e++] = 1;
        }
        entries[12] = point.x;
        entries[13] = point.y;
        entries[14] = point.lengthSquared();
        entries[15] = 1;

        Matrix4x4 matrix4x4 = new Matrix4x4(entries);
        return matrix4x4.getDeterminant() > 0;
    }

    private static class Matrix4x4
    {
        private final double rows[][];

        Matrix4x4(double... entries)
        {
            assert entries.length == 16;

            rows = new double[4][];
            for (int i = 0, r = 0; r < 4; ++r)
            {
                rows[r] = new double[4];
                for (int c = 0; c < 4; ++c)
                {
                    rows[r][c] = entries[i++];
                }
            }
        }

        double getDeterminant()
        {
            double determinant = 0;
            double[] row = rows[0];
            for (int i = 0; i < 4; ++i)
            {
                double sub = getSubdeterminant(i);
                determinant += row[i] * ((i & 1) == 0 ? +sub : -sub);
            }
            return determinant;
        }

        private double getSubdeterminant(int column)
        {
            double determinant = 0;
            for (int i = 0; i < 4; ++i)
            {
                double addend = 1;
                double subtrahend = 1;

                for (int a = 1, s = 3, j = 0; j < 4; ++j)
                {
                    if (j == column)
                    {
                        continue;
                    }

                    addend *= rows[a++][j];
                    subtrahend *= rows[s--][j];
                }

                determinant += addend;
                determinant -= subtrahend;
            }
            return determinant;
        }
    }

}
