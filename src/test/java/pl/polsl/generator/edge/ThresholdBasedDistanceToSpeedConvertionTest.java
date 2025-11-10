package pl.polsl.generator.edge;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static java.lang.String.format;

/**
 *
 * @author Kay Jay O'Nail
 */
public class ThresholdBasedDistanceToSpeedConvertionTest
{
    
    public ThresholdBasedDistanceToSpeedConvertionTest()
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
     * Test of speed method, of class ThresholdBasedDistanceToSpeedConvertion.
     */
    @Test
    public void testSpeed()
    {
        System.out.println("speed");
        
        double thresholds[] = new double[] { 10, 20, 30, 40 };
        double speeds[] = new double[] { 1, 2, 3, 4, 5 };
        ThresholdBasedDistanceToSpeedConvertion instance = new ThresholdBasedDistanceToSpeedConvertion(thresholds, speeds);
        
        double argument[] = new double[] { 5, 10, 15, 20, 25, 30, 35, 40 };
        double expectedResult[] = new double[] { 1, 2, 2, 3, 3, 4, 4, 5 };
        
        assert argument.length == expectedResult.length:
                "Wrongly designed test! There shall be as many arguments as expected results!";
        
        for (int i = 0; i < argument.length; ++i)
        {
            double result = instance.speed(argument[i]);
            if (result != expectedResult[i])
            {
                fail(format("For argument %f result %f was obtained instead of %f.\n",
                        argument[i],
                        result,
                        expectedResult[i]));
            }
        }
    }
    
}
