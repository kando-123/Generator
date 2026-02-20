This program serves generating problems for another project to solve, Time Dependent Vehicle Routing Problem.

The main task of the program is to generate a graph of customers; the other parameters of the problem are given directly in the configuration file. The algorithm of graph generation proceeds as follows. First, the coordinates of the vertices are generated. Then for every vertex, the custmer demand quantity is generated. Next, based on the demand, the service time is generated. That is followed by connecting the vertices with edges (in a triangulation method). For the edges, travel times is generated. This is achieved through estimating the speed on the way from one vertex to another which contributes to travel times at the times of the day. The generated information is compiled in a JSON object and saved to a file.

The user inputs in the configuration file: the vertex count, the total demand that shall be dispersed among the clients, the unit of the demand (the quantity by whose multiples the demands can differ). The user inputs also how the points, demand, service time and edges should be generated. In this version of the program, single implementations were provided. OrthogonalPointGenerator generates points from a given rectangle. UniformDemandGenerator assigns the demanded quantity uniformly. BilinearServiceTimeFunction generates a value between two linear functions for given argument (from a1 * x to a2 * x). DelaunayConnector performs Delaunay triangulation. It removes edges that create too narrow triangles but it ensures the graph remains connected. ScaledEdgeGenerator determines the time of travel through the edges is depending on their length. The shortest edge is mapped to the minimal speed, the longest edge - to the maximal.

Thanks to application of the Dependency Inversion Principle, the program is open for extending it by new generation options.

The program uses dependencies:
  com.google.gson
  org.locationtech.jts
