package pl.polsl.generator.triangle;

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
public class TriangleTest
{

    public TriangleTest()
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
     * Test of getPoints method, of class Triangle.
     */
    @Test
    public void testGetPointsCounterclockwiseScenario()
    {
        System.out.println("getPoints");
        
        Point[] points = new Point[]
        {
            new Point(-1, -1), new Point(2, 0), new Point(0, 2)
        };
        
        Triangle instance = new Triangle(points[0], points[1], points[2]);
        
        Point[] expectedResult = points;
        Point[] actualResult = instance.getPoints();

        if (!expectedResult[0].equals(actualResult[0]))
        {
            fail("Incorrect order of points.");
        }
        if (!expectedResult[1].equals(actualResult[1]))
        {
            fail("Incorrect order of points.");
        }
        if (!expectedResult[2].equals(actualResult[2]))
        {
            fail("Incorrect order of points.");
        }
    }
    
    @Test
    public void testGetPointsClockwiseScenario()
    {
        System.out.println("getPoints");
        
        Point[] points = new Point[]
        {
            new Point(-1, -1), new Point(0, 2), new Point(2, 0)
        };
        
        Triangle instance = new Triangle(points[0], points[1], points[2]);
        
        Point[] expectedResult = points;
        Point[] actualResult = instance.getPoints();

        if (!expectedResult[0].equals(actualResult[0]))
        {
            fail("Incorrect order of points.");
        }
        if (!expectedResult[1].equals(actualResult[2]))
        {
            fail("Incorrect order of points.");
        }
        if (!expectedResult[2].equals(actualResult[1]))
        {
            fail("Incorrect order of points.");
        }
    }

    /**
     * Test of getSides method, of class Triangle.
     */
//     @Test
    public void testGetSides()
    {
//        System.out.println("getSides");
//        Triangle instance = null;
//        double[] expResult = null;
//        double[] result = instance.getSides();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnglesInRadians method, of class Triangle.
     */
//    @Test
    public void testGetAnglesInRadians()
    {
//        System.out.println("getAnglesInRadians");
//        Triangle instance = null;
//        double[] expResult = null;
//        double[] result = instance.getAnglesInRadians();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnglesInDegrees method, of class Triangle.
     */
//    @Test
    public void testGetAnglesInDegrees()
    {
//        System.out.println("getAnglesInDegrees");
//        Triangle instance = null;
//        double[] expResult = null;
//        double[] result = instance.getAnglesInDegrees();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinimalAngleInRadians method, of class Triangle.
     */
//    @Test
    public void testGetMinimalAngleInRadians()
    {
//        System.out.println("getMinimalAngleInRadians");
//        Triangle instance = null;
//        double expResult = 0.0;
//        double result = instance.getMinimalAngleInRadians();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinimalAngleInDegrees method, of class Triangle.
     */
//    @Test
    public void testGetMinimalAngleInDegrees()
    {
//        System.out.println("getMinimalAngleInDegrees");
//        Triangle instance = null;
//        double expResult = 0.0;
//        double result = instance.getMinimalAngleInDegrees();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getPerimeter method, of class Triangle.
     */
//    @Test
    public void testGetPerimeter()
    {
//        System.out.println("getPerimeter");
//        Triangle instance = null;
//        double expResult = 0.0;
//        double result = instance.getPerimeter();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getArea method, of class Triangle.
     */
//    @Test
    public void testGetArea()
    {
//        System.out.println("getArea");
//        Triangle instance = null;
//        double expResult = 0.0;
//        double result = instance.getArea();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}
