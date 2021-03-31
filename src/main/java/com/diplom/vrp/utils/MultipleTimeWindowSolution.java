package com.diplom.vrp.utils;

import com.diplom.vrp.exceptions.EmptyResponseException;
import com.diplom.vrp.exceptions.ParameterIsNullOrLessThanZeroException;
import com.diplom.vrp.models.DepotModel;
import com.diplom.vrp.models.ServiceModel;
import com.diplom.vrp.models.VrpModel;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.ManhattanCosts;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.io.problem.VrpXMLWriter;
import org.apache.http.Header;
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
import org.json.JSONException;
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

    private static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    private int getTravelDurationWithTraffic(double x1, double y1, double x2, double y2) throws Exception{

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
            Header rateLimitHeader = response.getHeaders("X-MS-BM-WS-INFO")[0];
            if (rateLimitHeader.getValue().equals(String.valueOf(1))){
                wait(3000);
            }
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Error with VE API call. URI of the call: " + request.getURI());
                return travelDuration;
            }
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JSONObject jsonResult = new JSONObject(result);
                JSONObject trafficRoute = jsonResult.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources").getJSONObject(0);
                travelDuration = trafficRoute.getInt("travelDurationTraffic");
                return travelDuration;
            }
        }
        return travelDuration;
    }

    public String solve(VrpModel model){
        ModelValidator validator = new ModelValidator();
        model = validator.validateModel(model);
        logger.info("Input model: " + model.toString());
        final int WEIGHT_INDEX = 0; //0 means weight (e.g. 2700kg), 1 means volume (e.g. 17m^3)

        /*VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("VRP")
                .addCapacityDimension(WEIGHT_INDEX, model.getVehicleCapacity()).setCostPerWaitingTime(model.getCostPerWaitingTime());
        VehicleType vehicleType = vehicleTypeBuilder.build();

        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance(model.getVehicleType());
        vehicleBuilder.setStartLocation(Location.newInstance(model.getVehicleStartCoordinateX(), model.getVehicleStartCoordinateY()));
        vehicleBuilder.setType(vehicleType);
        VehicleImpl vehicle = vehicleBuilder.build();*/

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
        int depotCounter = 1;
        for (DepotModel depotModel: model.getDepots()) {
                VehicleTypeImpl vehicleType = VehicleTypeImpl.Builder.newInstance(depotCounter + "_type")
                        .addCapacityDimension(WEIGHT_INDEX, depotModel.getVehicleCapacity())
                        .setCostPerWaitingTime(depotModel.getCostPerWaitingTime()).build();
                VehicleImpl vehicle = VehicleImpl.Builder.newInstance(depotModel.getVehicleType())
                        .setStartLocation(Location.newInstance(depotModel.getVehicleStartCoordinateX(), depotModel.getVehicleStartCoordinateY()))
                        .setType(vehicleType).build();
                vrpBuilder.addVehicle(vehicle);
            depotCounter++;
        }

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

        if (solutionArray.getJSONObject(0).has("routes")) {
            if (model.getDepots().size() == 1) {
                for (int i = 0; i < solutionArray.length(); i++) {
                    //JSONArray tmp = solutionArray.getJSONObject(i).getJSONObject("routes").getJSONObject("route").getJSONArray("act");
                    JSONArray tmp = new JSONArray();
                    try{
                        tmp = solutionArray.getJSONObject(i).getJSONObject("routes").getJSONObject("route").getJSONArray("act");
                        solutionArray.getJSONObject(i).getJSONObject("routes").getJSONObject("route").put("lat", model.getDepots().get(0).getVehicleStartCoordinateX());
                        solutionArray.getJSONObject(i).getJSONObject("routes").getJSONObject("route").put("lng", model.getDepots().get(0).getVehicleStartCoordinateY());
                    } catch (JSONException e){
                        logger.warn("Something wrong happened while getting an array " + e);
                        tmp.put(solutionArray.getJSONObject(i).getJSONObject("routes").getJSONObject("route").getJSONObject("act"));
                    }
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
                        for (ServiceModel serviceModel : serviceModelList) {
                            if (serviceModel.getServiceId().equals(String.valueOf(id))) {
                                if (j == 0) {
                                    try {
                                        travelDurationWithTraffic = getTravelDurationWithTraffic(model.getDepots().get(0).getVehicleStartCoordinateX(), model.getDepots().get(0).getVehicleStartCoordinateY(),
                                                serviceModel.getLocationX(), serviceModel.getLocationY());
                                        act.put("travelDurationTraffic", travelDurationWithTraffic);
                                        act.put("timeUnit", "seconds");
                                        act.put("lat", serviceModel.getLocationX());
                                        act.put("lng", serviceModel.getLocationY());
                                        nextAct.put("travelDurationTraffic", travelDurationWithTraffic);
                                        nextAct.put("timeUnit", "seconds");
                                        nextAct.put("lat", serviceModel.getLocationX());
                                        nextAct.put("lng", serviceModel.getLocationY());
                                    } catch (Exception e) {
                                        logger.error("Failed to send data to VE: " + e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                                for (ServiceModel nextModel : serviceModelList) {
                                    if (nextId != -1 && nextModel.getServiceId().equals(String.valueOf(nextId))) {
                                        try {
                                            travelDurationWithTraffic = getTravelDurationWithTraffic(serviceModel.getLocationX(), serviceModel.getLocationY(),
                                                    nextModel.getLocationX(), nextModel.getLocationY());
                                            nextAct.put("travelDurationTraffic", travelDurationWithTraffic);
                                            nextAct.put("timeUnit", "seconds");
                                            nextAct.put("lat", nextModel.getLocationX());
                                            nextAct.put("lng", nextModel.getLocationY());
                                        } catch (Exception e) {
                                            logger.error("Failed to send data to VE: " + e);
                                        }
                                    }
                                }

                            } else continue;
                        }
                    }
                }
            } else{
                JSONArray routes = new JSONArray();
                try {
                    routes = solutionArray.getJSONObject(0).getJSONObject("routes").getJSONArray("route");
                } catch (JSONException e){
                    logger.warn("Something wrong happened while getting an array " + e);
                    routes.put(solutionArray.getJSONObject(0).getJSONObject("routes").getJSONObject("route"));
                }
                for (int i = 0; i < routes.length(); i++) {
                    String vehicleId = null;
                    JSONObject route = routes.getJSONObject(i);
                    for (DepotModel depotModel: model.getDepots()) {
                        if (depotModel.getVehicleType().equals(route.getString("vehicleId"))){
                            vehicleId = depotModel.getVehicleType();
                            route.put("lat", depotModel.getVehicleStartCoordinateX());
                            route.put("lng", depotModel.getVehicleStartCoordinateY());
                        }
                    }
                    JSONArray acts = new JSONArray();
                    try{
                        acts = route.getJSONArray("act");
                    } catch (JSONException e){
                        logger.warn("Something wrong happened while getting an array " + e);
                        acts.put(route.getJSONObject("act"));
                    }
                    for (int j = 0; j < acts.length(); j++) {
                        JSONObject act = acts.getJSONObject(j);
                        int id = act.getInt("serviceId");
                        JSONObject nextAct = null;
                        int nextId = -1;
                        if (j + 1 != acts.length()) {
                            nextAct = acts.getJSONObject(j + 1);
                            nextId = nextAct.getInt("serviceId");
                        }
                        for (ServiceModel serviceModel : serviceModelList) {
                            if (serviceModel.getServiceId().equals(String.valueOf(id))) {
                                if (j == 0) {
                                    for (DepotModel depotModel: model.getDepots()) {
                                        if (depotModel.getVehicleType().equals(vehicleId)) {
                                            try {
                                                travelDurationWithTraffic = getTravelDurationWithTraffic(depotModel.getVehicleStartCoordinateX(), depotModel.getVehicleStartCoordinateY(),
                                                        serviceModel.getLocationX(), serviceModel.getLocationY());
                                                act.put("travelDurationTraffic", travelDurationWithTraffic);
                                                act.put("timeUnit", "seconds");
                                                act.put("lat", serviceModel.getLocationX());
                                                act.put("lng", serviceModel.getLocationY());
                                                nextAct.put("travelDurationTraffic", travelDurationWithTraffic);
                                                nextAct.put("timeUnit", "seconds");
                                                nextAct.put("lat", serviceModel.getLocationX());
                                                nextAct.put("lng", serviceModel.getLocationY());
                                            } catch (Exception e) {
                                                logger.error("Failed to send data to VE: " + e);
                                            }
                                        }
                                    }
                                }
                                for (ServiceModel nextModel : serviceModelList) {
                                    if (nextId != -1 && nextModel.getServiceId().equals(String.valueOf(nextId))) {
                                        try {
                                            travelDurationWithTraffic = getTravelDurationWithTraffic(serviceModel.getLocationX(), serviceModel.getLocationY(),
                                                    nextModel.getLocationX(), nextModel.getLocationY());
                                            nextAct.put("travelDurationTraffic", travelDurationWithTraffic);
                                            nextAct.put("timeUnit", "seconds");
                                            nextAct.put("lat", nextModel.getLocationX());
                                            nextAct.put("lng", nextModel.getLocationY());
                                        } catch (Exception e) {
                                            logger.error("Failed to send data to VE: " + e);
                                        }
                                    }
                                }

                            } else continue;
                        }
                    }
                }
            }
        } else throw new EmptyResponseException("Nothing to show. None of the services could be assigned to provided vehicle(s)");
        //SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);
        logger.info("Problem is solved, returning solution...");
        return obj.toString();
    }
}
