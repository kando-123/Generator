package pl.polsl.generator;

import com.google.gson.*;
import java.io.*;
import pl.polsl.generator.connection.*;
import pl.polsl.generator.demand.*;
import pl.polsl.generator.edge.*;
import pl.polsl.generator.graph.*;
import pl.polsl.generator.point.*;
import pl.polsl.generator.time.*;

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
        if (args.length < 2)
        {
            System.out.println("""
                               Args:
                                   [0] configuration JSON file (required),
                                   [1] path to output file (req.),
                                   [2] options: \"-over\" to overwrite, \"-next\" to add number (optional)
                               """);
            return;
        }
        
        File input = new File(args[0]);
        if (!input.canRead())
        {
            System.out.println("Cannot read file " + args[0]);
            return;
        }
        
        File base = new File(args[1]);
        String parent = base.getParent();
        String name = base.getName();
        
        int dotIndex = name.lastIndexOf('.');
        String baseName, extension;
        baseName = dotIndex > 0 ? name.substring(0, dotIndex) : name;
        extension = dotIndex > 0 ? name.substring(dotIndex) : "";
        
        File output = base;
        for (int i = 0; output.exists(); ++i)
        {
            String newName = String.format("%s_%d%s", baseName, i, extension);
            output = parent != null ? new File(parent, newName) : new File(newName);
        }
        
        try (FileReader reader = new FileReader(input); FileWriter writer = new FileWriter(output))
        {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            GraphGenerator generator = createGraphGenerator(root);
            Graph graph = generator.generateGraph();
            
            int startTime = root.get("start_time").getAsInt();
            int interval = root.get("interval").getAsInt();
            
            int vehicleCount = root.get("vehicle_count").getAsInt();
            double vehicleCapacity = root.get("vehicle_capacity").getAsDouble();
            JsonObject vehicleObject = new JsonObject();
            vehicleObject.addProperty("count", vehicleCount);
            vehicleObject.addProperty("capacity", vehicleCapacity);
            
            JsonObject paramsObject = new JsonObject();
            paramsObject.addProperty("start", startTime);
            paramsObject.addProperty("interval", interval);
            paramsObject.addProperty("depot", 0);
            paramsObject.add("vehicle", vehicleObject);
            
            JsonObject problemObject = new JsonObject();
            problemObject.add("graph", graph.toJson());
            problemObject.add("params", paramsObject);
            
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(problemObject, writer);
            System.out.println("Saved: \"" + output.getAbsolutePath() + "\"");
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
    
    private static GraphGenerator createGraphGenerator(JsonElement configuration) throws Exception
    {
        var builder = new GraphGenerator.GraphGeneratorBuilder();
        JsonObject object = configuration.getAsJsonObject();
        
        JsonObject parameters = object.get("point_generator").getAsJsonObject();
        PointGenerator pointGenerator = createPointGenerator(parameters);
        
        parameters = object.get("demand_generator").getAsJsonObject();
        DemandGenerator demandGenerator = createDemandGenerator(parameters);
        
        parameters = object.get("service_time_function").getAsJsonObject();
        ServiceTimeFunction serviceTimeFunction = createServiceTimeFunction(parameters);
        
        parameters = object.get("connector").getAsJsonObject();
        Connector connector = createConnector(parameters);
        
        parameters = object.get("edge_generator").getAsJsonObject();
        EdgeGenerator edgeGenerator = createEdgeGenerator(parameters);
        
        int vertexCount = object.get("vertex_count").getAsInt();
        double totalDemand = object.get("total_demand").getAsDouble();
        double demandUnit = object.get("demand_unit").getAsDouble();
        
        builder.setPointGenerator(pointGenerator)
                .setDemandGenerator(demandGenerator)
                .setServiceTimeFunction(serviceTimeFunction)
                .setConnector(connector)
                .setEdgeGenerator(edgeGenerator)
                .setVertexCount(vertexCount)
                .setTotalDemand(totalDemand)
                .setDemandUnit(demandUnit);
        return builder.build();
    }
    
    private static PointGenerator createPointGenerator(JsonObject parameters) throws Exception
    {
        String type = parameters.get("type").getAsString();
        PointGenerator result = switch (type)
        {
            case "orthogonal" ->
            {
                int width = parameters.get("width").getAsInt();
                int height = parameters.get("height").getAsInt();
                if (parameters.has("seed"))
                {
                    long seed = parameters.get("seed").getAsLong();
                    yield new OrthogonalPointGenerator(width, height, seed);
                }
                else
                {
                    yield new OrthogonalPointGenerator(width, height);
                }
            }
            default ->
            {
                yield null;
            }
        };
        if (result == null)
        {
            throw new IllegalArgumentException("Invalid type of point generator.");
        }
        return result;
    }
    
    private static DemandGenerator createDemandGenerator(JsonObject parameters)
    {
        String type = parameters.get("type").getAsString();
        DemandGenerator result = switch (type)
        {
            case "uniform" ->
            {
                if (parameters.has("seed"))
                {
                    long seed = parameters.get("seed").getAsLong();
                    yield new UniformDemandGenerator(seed);
                }
                else
                {
                    yield new UniformDemandGenerator();
                }
            }
            default ->
            {
                yield null;
            }
        };
        if (result == null)
        {
            throw new IllegalArgumentException("Invalid type of demand generator.");
        }
        return result;
    }
    
    private static ServiceTimeFunction createServiceTimeFunction(JsonObject parameters)
    {
        String type = parameters.get("type").getAsString();
        ServiceTimeFunction result = switch (type)
        {
            case "bilinear" ->
            {
                double lowerSlope = parameters.get("lower_slope").getAsDouble();
                double upperSlope = parameters.get("upper_slope").getAsDouble();
                if (parameters.has("seed"))
                {
                    long seed = parameters.get("seed").getAsLong();
                    yield new BilinearServiceTimeFunction(lowerSlope, upperSlope, seed);
                }
                else
                {
                    yield new BilinearServiceTimeFunction(lowerSlope, upperSlope);
                }
            }
            default ->
            {
                yield null;
            }
        };
        if (result == null)
        {
            throw new IllegalArgumentException("Invalid type of service time function.");
        }
        return result;
    }
    
    private static Connector createConnector(JsonObject parameters)
    {
        String type = parameters.get("type").getAsString();
        Connector result = switch (type)
        {
            case "delaunay" ->
            {
                double minAngleInDegrees = parameters.get("min_angle_deg").getAsDouble();
                yield new DelaunayConnector(minAngleInDegrees);
            }
            default ->
            {
                yield null;
            }
        };
        if (result == null)
        {
            throw new IllegalArgumentException("Invalid type of connector.");
        }
        return result;
    }
    
    private static EdgeGenerator createEdgeGenerator(JsonObject parameters)
    {
        String type = parameters.get("type").getAsString();
        EdgeGenerator result = switch (type)
        {
            case "scaled" ->
            {
                JsonArray fractions = parameters.get("fractions").getAsJsonArray();
                double[] speedFractions = fractions.asList().stream()
                        .mapToDouble(JsonElement::getAsDouble)
                        .toArray();
                double minSpeed = parameters.get("min_speed").getAsDouble();
                double maxSpeed = parameters.get("max_speed").getAsDouble();
                yield new ScaledEdgeGenerator(speedFractions, minSpeed, maxSpeed);
            }
            default ->
            {
                yield null;
            }
        };
        if (result == null)
        {
            throw new IllegalArgumentException("Invalid type of edge generator.");
        }
        return result;
    }
}
