package com.diplom.vrp.utils;

import com.diplom.vrp.exceptions.ParameterIsNullOrLessThanZeroException;
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
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;


public class MultipleTimeWindowSolution {

    private static VrpModel validateModel(VrpModel model){
        if (model.getVehicleType() == null || model.getVehicleType().equals(""))
            model.setVehicleType("Vehicle");
        if (model.getCostPerWaitingTime() == null || model.getCostPerWaitingTime() <= 0)
            throw new ParameterIsNullOrLessThanZeroException("costPerWaitingTime");
        if (model.getVehicleCapacity() == null || model.getVehicleCapacity() <= 0)
            throw new ParameterIsNullOrLessThanZeroException("vehicleCapacity");
        if (model.getVehicleStartCoordinateX() == null || model.getVehicleStartCoordinateX() <= 0)
            throw new ParameterIsNullOrLessThanZeroException("vehicleStartCoordinateX");
        if (model.getVehicleStartCoordinateY() == null || model.getVehicleStartCoordinateY() <= 0)
            throw new ParameterIsNullOrLessThanZeroException("vehicleStartCoordinateY");
        for (ServiceModel serviceModel: model.getServices()) {
            if (serviceModel.getServiceId() == null || serviceModel.getServiceId().isEmpty())
                throw new ParameterIsNullOrLessThanZeroException("services.serviceId");
            if (serviceModel.getEarliest() == null || serviceModel.getEarliest() <= 0)
                throw new ParameterIsNullOrLessThanZeroException("services.earliest");
            if (serviceModel.getLatest() == null || serviceModel.getLatest() <= 0)
                throw new ParameterIsNullOrLessThanZeroException("services.latest");
            if (serviceModel.getDimensionValue() == null || serviceModel.getDimensionValue() <= 0)
                throw new ParameterIsNullOrLessThanZeroException("services.dimensionValue");
            if (serviceModel.getLocationX() == null || serviceModel.getLocationX() <= 0)
                throw new ParameterIsNullOrLessThanZeroException("services.locationX");
            if (serviceModel.getLocationY() == null || serviceModel.getLocationY() <= 0)
                throw new ParameterIsNullOrLessThanZeroException("services.locationY");
        }
        return model;
    }

    public static String solve(VrpModel model){
        model = validateModel(model);
        final int WEIGHT_INDEX = 0;
        VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("Peshexod")
                .addCapacityDimension(WEIGHT_INDEX, 10).setCostPerWaitingTime(1.);
        VehicleType vehicleType = vehicleTypeBuilder.build();

        /*
         * get a vehicle-builder and build a vehicle located at (10,10) with type "vehicleType"
         */
        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("Avtovaz");
        vehicleBuilder.setStartLocation(Location.newInstance(1, 1));
        vehicleBuilder.setType(vehicleType);
        VehicleImpl vehicle = vehicleBuilder.build();

        /*
         * build services at the required locations, each with a capacity-demand of 1.
         */
        Service service1 = Service.Builder.newInstance("1")
                //.addTimeWindow(50,100)
                .addTimeWindow(20,35)
                .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(10, 0)).build();

        Service service2 = Service.Builder.newInstance("2")
                .addSizeDimension(WEIGHT_INDEX, 1)
                .setServiceTime(10).setLocation(Location.newInstance(25,12))
                .setLocation(Location.newInstance(20, 0)).setServiceTime(10).build();

        Service service3 = Service.Builder.newInstance("3")
                .addTimeWindow(35, 50)
                .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(30, 0)).build();

        Service service4 = Service.Builder.newInstance("4")
                .addTimeWindow(120, 140)
                .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(40, 0)).build();

        Service service5 = Service.Builder.newInstance("5")
                .addTimeWindow(60,100)
                .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(20, 0)).build();


        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        vrpBuilder.addVehicle(vehicle);
        vrpBuilder.addJob(service1).addJob(service2)
                .addJob(service3)
                .addJob(service4)
                .addJob(service5)
        ;
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
        JSONObject soapDataInJsonObject = null;
        try {
            File myObj = new File("problem-with-solution.xml");
            Scanner myReader = new Scanner(myObj);
            String data = null;
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            String dataWithoutNull = data.substring(4);
            soapDataInJsonObject = XML.toJSONObject(dataWithoutNull);
            //System.out.println(soapDataInJsonObject);
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);
        return soapDataInJsonObject.toString();
        //new Plotter(problem,bestSolution).setLabel(Plotter.Label.ID).plot("F:/xlam/plot", "mine");

        //new GraphStreamViewer(problem, bestSolution).labelWith(GraphStreamViewer.Label.ID).setRenderDelay(200).display();
    }
}
