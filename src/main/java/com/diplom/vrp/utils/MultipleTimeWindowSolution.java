package com.diplom.vrp.utils;

import com.diplom.vrp.models.ServiceModel;
import com.diplom.vrp.models.VrpModel;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.ManhattanCosts;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.io.problem.VrpXMLWriter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;


public class MultipleTimeWindowSolution {

    private static Logger logger = LoggerFactory.getLogger(MultipleTimeWindowSolution.class);
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private static final String BASE_VIRTUAL_EARTH_URL = "http://dev.virtualearth.net/REST/v1/Routes";
    private final String API_KEY = "An_5PxQrt00CCd5u7R_FvByn0TvTTnz8JRYk_vgLSUeAlnh0o9V_99gllTlD0CaV";


    private int sendGetToVE(double x1, double y1, double x2, double y2) throws Exception{

        HttpGet request = new HttpGet(BASE_VIRTUAL_EARTH_URL);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("waypoint.0", x1 + "," + y1));
        urlParameters.add(new BasicNameValuePair("waypoint.1", x2 + "," + y2));
        urlParameters.add(new BasicNameValuePair("optimize", "timeWithTraffic"));
        urlParameters.add(new BasicNameValuePair("key", API_KEY));

        URI uri = new URIBuilder(request.getURI()).setParameters(urlParameters).build();
        request.addHeader(HttpHeaders.ACCEPT, "application/json");
        request.setURI(uri);
        logger.info(String.valueOf(request.getURI()));
        int travelDuration = -1;
        try (CloseableHttpResponse response = httpClient.execute(request)){
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Error with VE API call. URI of the call: " + request.getURI());
            }
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JSONObject jsonResult = new JSONObject(result);
                JSONObject trafficRoute = jsonResult.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources").getJSONObject(0);
                travelDuration = trafficRoute.getInt("travelDurationTraffic");
                return (travelDuration);
            }
        }
        return travelDuration;
    }

    public String solve(VrpModel model){
        ModelValidator validator = new ModelValidator();
        model = validator.validateModel(model);
        logger.info("Input model: " + model.toString());
        final int WEIGHT_INDEX = 0; //0 means weight (e.g. 2700kg), 1 means volume (e.g. 17m^3)
        VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("VRP")
                .addCapacityDimension(WEIGHT_INDEX, model.getVehicleCapacity()).setCostPerWaitingTime(model.getCostPerWaitingTime());
        VehicleType vehicleType = vehicleTypeBuilder.build();

        /*
         * get a vehicle-builder and build a vehicle located at (10,10) with type "vehicleType"
         */
        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance(model.getVehicleType());
        vehicleBuilder.setStartLocation(Location.newInstance(model.getVehicleStartCoordinateX(), model.getVehicleStartCoordinateY()));
        vehicleBuilder.setType(vehicleType);
        VehicleImpl vehicle = vehicleBuilder.build();

        /*
         * build services at the required locations, each with a capacity-demand of 1.
         */
        List<Service> serviceList = new ArrayList<>();
        List<ServiceModel> serviceModelList = model.getServices();

        for (ServiceModel serviceModel: serviceModelList) {
            Service service = Service.Builder.newInstance(serviceModel.getServiceId())
                    .addTimeWindow(serviceModel.getEarliest(), serviceModel.getLatest())
                    .addSizeDimension(WEIGHT_INDEX, serviceModel.getDimensionValue())
                    .setLocation(Location.newInstance(serviceModel.getLocationX(), serviceModel.getLocationY()))
                    .build();
            serviceList.add(service);
        }

        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        vrpBuilder.addVehicle(vehicle);
        for (Service service: serviceList) {
            vrpBuilder.addJob(service);
        }

        vrpBuilder.setFleetSize(VehicleRoutingProblem.FleetSize.FINITE);
        vrpBuilder.setRoutingCost(new ManhattanCosts());
        VehicleRoutingProblem problem = vrpBuilder.build();

        /*
         * get the algorithm out-of-the-box.
         */
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);

        /*
         * and search a solution
         */
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();

        /*
         * get the best
         */
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);


        new VrpXMLWriter(problem, solutions).write("problem-with-solution.xml");
        JSONObject jspritXMLOutputInJSON = null;
        try {
            logger.info("Trying to convert XML to JSON");
            File myObj = new File("problem-with-solution.xml");
            Scanner myReader = new Scanner(myObj);
            String data = null;
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            String dataWithoutNull = Objects.requireNonNull(data).substring(4);
            jspritXMLOutputInJSON = XML.toJSONObject(dataWithoutNull);
            myReader.close();
        } catch (FileNotFoundException e) {
            logger.error("Converting failed. File is not found " + e);
        }

        JSONObject obj = new JSONObject(jspritXMLOutputInJSON.toString());
        JSONArray solutionArray = obj.getJSONObject("problem").getJSONObject("solutions").getJSONArray("solution");

        int travelDurationWithTraffic = 0;

        for (int i = 0; i < solutionArray.length(); i++) {
            JSONArray tmp = solutionArray.getJSONObject(i).getJSONObject("routes").getJSONObject("route").getJSONArray("act");
            travelDurationWithTraffic = 0;
            for (int j = 0; j < tmp.length(); j++) {

                JSONObject act = tmp.getJSONObject(j);
                int id = act.getInt("serviceId");

                JSONObject nextAct = null;
                int nextId = -1;
                if (j + 1 != tmp.length()) {
                    nextAct = tmp.getJSONObject(j + 1);
                    nextId = nextAct.getInt("serviceId");
                }
                for (ServiceModel serviceModel: serviceModelList) {
                    if (serviceModel.getServiceId().equals(String.valueOf(id))) {
                        if (j == 0) {
                            try {
                                travelDurationWithTraffic += sendGetToVE(model.getVehicleStartCoordinateX(), model.getVehicleStartCoordinateY(),
                                        serviceModel.getLocationX(), serviceModel.getLocationY());
                            } catch (Exception e) {
                                logger.error("Failed to send data to VE: " + e);
                            }
                        }
                        for (ServiceModel nextModel: serviceModelList) {
                            if (nextId != -1 && nextModel.getServiceId().equals(String.valueOf(nextId))){
                                try {
                                    travelDurationWithTraffic += sendGetToVE(serviceModel.getLocationX(), serviceModel.getLocationY(),
                                            nextModel.getLocationX(), nextModel.getLocationY());
                                } catch (Exception e) {
                                    logger.error("Failed to send data to VE: " + e);
                                }
                            }
                        }

                    } else continue;
                }
            }
        }


        SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);
        logger.info("Problem is solved, returning solution...");
        return jspritXMLOutputInJSON.toString();
    }
}
