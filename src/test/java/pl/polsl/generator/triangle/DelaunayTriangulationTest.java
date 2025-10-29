/*
 * 
 * 
 */
package pl.polsl.generator.triangle;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.polsl.generator.point.Point;

/**
 *
 * @author Kay Jay O'Nail
 */
public class DelaunayTriangulationTest
{

    public DelaunayTriangulationTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of triangulate method, of class DelaunayTriangulation.
     */
    @Test
    public void testTriangulate()
    {
        System.out.println("Triangulate");

        Point[] points = new Point[]
        {
            new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(2, 2)
        };

        DelaunayTriangulation instance = new DelaunayTriangulation();
        
        Set<IntPair> expected = new HashSet<>();
        expected.add(new OrderedIntPair(0, 1));
        expected.add(new OrderedIntPair(0, 2));
        expected.add(new OrderedIntPair(1, 2));
        expected.add(new OrderedIntPair(1, 3));
        expected.add(new OrderedIntPair(2, 3));
        
        Set<IntPair> actual = new HashSet<>();
        var result = instance.triangulate(points);
        actual.addAll(Arrays.asList(result));
        
        if (!expected.equals(actual))
        {
            fail("Triangulation failed.");
        }
    }

}
