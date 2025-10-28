package pl.polsl.generator;

import java.util.Arrays;
import pl.polsl.generator.point.*;

/**
 * The program generates problem files for the algorithms
 * solving the Time-Dependent Vehicle Routing Problem.
 * 
 * Input: configuration file.
 * Output: graph(s) generated according to the configuration.
 * 
 * Contents of the configuration file:
 * (1) type of vertex position generation and its parameters,
 * (2) type of demand quantity generation and its parameters,
 * (3) type of edge travel time generation and its parameters.
 * 
 * @author Kay Jay O'Nail
 */
public class Generator
{
    public static void main(String[] args)
    {
        PointGenerator opg = new OrthogonalPointGenerator(800, 600);
        Point points[] = opg.generatePoints(10);
        System.out.println(Arrays.toString(points));
    }
}
