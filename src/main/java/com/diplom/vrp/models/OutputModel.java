package com.diplom.vrp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class Costs{
    @JsonProperty("wait")
    public int getWait() {
        return this.wait; }
    public void setWait(int wait) {
        this.wait = wait; }
    int wait;
    @JsonProperty("distance")
    public int getDistance() {
        return this.distance; }
    public void setDistance(int distance) {
        this.distance = distance; }
    int distance;
    @JsonProperty("service")
    public int getService() {
        return this.service; }
    public void setService(int service) {
        this.service = service; }
    int service;
    @JsonProperty("fixed")
    public int getFixed() {
        return this.fixed; }
    public void setFixed(int fixed) {
        this.fixed = fixed; }
    int fixed;
    @JsonProperty("time")
    public int getTime() {
        return this.time; }
    public void setTime(int time) {
        this.time = time; }
    int time;
}

class Dimension{
    @JsonProperty("index")
    public int getIndex() {
        return this.index; }
    public void setIndex(int index) {
        this.index = index; }
    int index;
    @JsonProperty("content")
    public int getContent() {
        return this.content; }
    public void setContent(int content) {
        this.content = content; }
    int content;
}

class CapacityDimensions{
    @JsonProperty("dimension")
    public Dimension getDimension() {
        return this.dimension; }
    public void setDimension(Dimension dimension) {
        this.dimension = dimension; }
    Dimension dimension;
}

class Type{
    @JsonProperty("costs")
    public Costs getCosts() {
        return this.costs; }
    public void setCosts(Costs costs) {
        this.costs = costs; }
    Costs costs;
    @JsonProperty("id")
    public String getId() {
        return this.id; }
    public void setId(String id) {
        this.id = id; }
    String id;
    @JsonProperty("capacity-dimensions")
    public CapacityDimensions getCapacityDimensions() {
        return this.capacityDimensions; }
    public void setCapacityDimensions(CapacityDimensions capacityDimensions) {
        this.capacityDimensions = capacityDimensions; }
    CapacityDimensions capacityDimensions;
}

class VehicleTypes{
    @JsonProperty("type")
    public Type getType() {
        return this.type; }
    public void setType(Type type) {
        this.type = type; }
    Type type;
}

class Act{
    @JsonProperty("endTime")
    public int getEndTime() {
        return this.endTime; }
    public void setEndTime(int endTime) {
        this.endTime = endTime; }
    int endTime;
    @JsonProperty("type")
    public String getType() {
        return this.type; }
    public void setType(String type) {
        this.type = type; }
    String type;
    @JsonProperty("serviceId")
    public int getServiceId() {
        return this.serviceId; }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId; }
    int serviceId;
    @JsonProperty("arrTime")
    public int getArrTime() {
        return this.arrTime; }
    public void setArrTime(int arrTime) {
        this.arrTime = arrTime; }
    int arrTime;
}

class Route{
    @JsonProperty("act")
    public List<Act> getAct() {
        return this.act; }
    public void setAct(List<Act> act) {
        this.act = act; }
    List<Act> act;
    @JsonProperty("driverId")
    public String getDriverId() {
        return this.driverId; }
    public void setDriverId(String driverId) {
        this.driverId = driverId; }
    String driverId;
    @JsonProperty("start")
    public int getStart() {
        return this.start; }
    public void setStart(int start) {
        this.start = start; }
    int start;
    @JsonProperty("end")
    public int getEnd() {
        return this.end; }
    public void setEnd(int end) {
        this.end = end; }
    int end;
    @JsonProperty("vehicleId")
    public String getVehicleId() {
        return this.vehicleId; }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId; }
    String vehicleId;
}

class Routes{
    @JsonProperty("route")
    public Route getRoute() {
        return this.route; }
    public void setRoute(Route route) {
        this.route = route; }
    Route route;
}

class Solution{
    @JsonProperty("routes")
    public Routes getRoutes() {
        return this.routes; }
    public void setRoutes(Routes routes) {
        this.routes = routes; }
    Routes routes;
    @JsonProperty("cost")
    public int getCost() {
        return this.cost; }
    public void setCost(int cost) {
        this.cost = cost; }
    int cost;
}

class Solutions{
    @JsonProperty("solution")
    public List<Solution> getSolution() {
        return this.solution; }
    public void setSolution(List<Solution> solution) {
        this.solution = solution; }
    List<Solution> solution;
}

class Coord{
    @JsonProperty("x")
    public int getX() {
        return this.x; }
    public void setX(int x) {
        this.x = x; }
    int x;
    @JsonProperty("y")
    public int getY() {
        return this.y; }
    public void setY(int y) {
        this.y = y; }
    int y;
}

class StartLocation{
    @JsonProperty("coord")
    public Coord getCoord() {
        return this.coord; }
    public void setCoord(Coord coord) {
        this.coord = coord; }
    Coord coord;
    @JsonProperty("id")
    public String getId() {
        return this.id; }
    public void setId(String id) {
        this.id = id; }
    String id;
}

class TimeSchedule{
    @JsonProperty("start")
    public int getStart() {
        return this.start; }
    public void setStart(int start) {
        this.start = start; }
    int start;
    @JsonProperty("end")
    public double getEnd() {
        return this.end; }
    public void setEnd(double end) {
        this.end = end; }
    double end;
}

class EndLocation{
    @JsonProperty("coord")
    public Coord getCoord() {
        return this.coord; }
    public void setCoord(Coord coord) {
        this.coord = coord; }
    Coord coord;
    @JsonProperty("id")
    public String getId() {
        return this.id; }
    public void setId(String id) {
        this.id = id; }
    String id;
}

class Vehicle{
    @JsonProperty("startLocation")
    public StartLocation getStartLocation() {
        return this.startLocation; }
    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation; }
    StartLocation startLocation;
    @JsonProperty("timeSchedule")
    public TimeSchedule getTimeSchedule() {
        return this.timeSchedule; }
    public void setTimeSchedule(TimeSchedule timeSchedule) {
        this.timeSchedule = timeSchedule; }
    TimeSchedule timeSchedule;
    @JsonProperty("returnToDepot")
    public boolean getReturnToDepot() {
        return this.returnToDepot; }
    public void setReturnToDepot(boolean returnToDepot) {
        this.returnToDepot = returnToDepot; }
    boolean returnToDepot;
    @JsonProperty("typeId")
    public String getTypeId() {
        return this.typeId; }
    public void setTypeId(String typeId) {
        this.typeId = typeId; }
    String typeId;
    @JsonProperty("id")
    public String getId() {
        return this.id; }
    public void setId(String id) {
        this.id = id; }
    String id;
    @JsonProperty("endLocation")
    public EndLocation getEndLocation() {
        return this.endLocation; }
    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation; }
    EndLocation endLocation;
}

class Vehicles{
    @JsonProperty("vehicle")
    public Vehicle getVehicle() {
        return this.vehicle; }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle; }
    Vehicle vehicle;
}

class TimeWindow{
    @JsonProperty("start")
    public int getStart() {
        return this.start; }
    public void setStart(int start) {
        this.start = start; }
    int start;
    @JsonProperty("end")
    public double getEnd() {
        return this.end; }
    public void setEnd(double end) {
        this.end = end; }
    double end;
}

class TimeWindows{
    @JsonProperty("timeWindow")
    public TimeWindow getTimeWindow() {
        return this.timeWindow; }
    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow; }
    TimeWindow timeWindow;
}

class Location{
    @JsonProperty("coord")
    public Coord getCoord() {
        return this.coord; }
    public void setCoord(Coord coord) {
        this.coord = coord; }
    Coord coord;
    @JsonProperty("id")
    public String getId() {
        return this.id; }
    public void setId(String id) {
        this.id = id; }
    String id;
}

class Service{
    @JsonProperty("duration")
    public int getDuration() {
        return this.duration; }
    public void setDuration(int duration) {
        this.duration = duration; }
    int duration;
    @JsonProperty("timeWindows")
    public TimeWindows getTimeWindows() {
        return this.timeWindows; }
    public void setTimeWindows(TimeWindows timeWindows) {
        this.timeWindows = timeWindows; }
    TimeWindows timeWindows;
    @JsonProperty("location")
    public Location getLocation() {
        return this.location; }
    public void setLocation(Location location) {
        this.location = location; }
    Location location;
    @JsonProperty("id")
    public int getId() {
        return this.id; }
    public void setId(int id) {
        this.id = id; }
    int id;
    @JsonProperty("type")
    public String getType() {
        return this.type; }
    public void setType(String type) {
        this.type = type; }
    String type;
    @JsonProperty("capacity-dimensions")
    public CapacityDimensions getCapacityDimensions() {
        return this.capacityDimensions; }
    public void setCapacityDimensions(CapacityDimensions capacityDimensions) {
        this.capacityDimensions = capacityDimensions; }
    CapacityDimensions capacityDimensions;
}

class Services{
    @JsonProperty("service")
    public List<Service> getService() {
        return this.service; }
    public void setService(List<Service> service) {
        this.service = service; }
    List<Service> service;
}

class ProblemType{
    @JsonProperty("fleetSize")
    public String getFleetSize() {
        return this.fleetSize; }
    public void setFleetSize(String fleetSize) {
        this.fleetSize = fleetSize; }
    String fleetSize;
}

public class OutputModel{
    @JsonProperty("xmlns")
    public String getXmlns() {
        return this.xmlns; }
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns; }
    String xmlns;
    @JsonProperty("vehicleTypes")
    public VehicleTypes getVehicleTypes() {
        return this.vehicleTypes; }
    public void setVehicleTypes(VehicleTypes vehicleTypes) {
        this.vehicleTypes = vehicleTypes; }
    VehicleTypes vehicleTypes;
    @JsonProperty("solutions")
    public Solutions getSolutions() {
        return this.solutions; }
    public void setSolutions(Solutions solutions) {
        this.solutions = solutions; }
    Solutions solutions;
    @JsonProperty("xsi:schemaLocation")
    public String getXsiSchemaLocation() {
        return this.xsiSchemaLocation; }
    public void setXsiSchemaLocation(String xsiSchemaLocation) {
        this.xsiSchemaLocation = xsiSchemaLocation; }
    String xsiSchemaLocation;
    @JsonProperty("vehicles")
    public Vehicles getVehicles() {
        return this.vehicles; }
    public void setVehicles(Vehicles vehicles) {
        this.vehicles = vehicles; }
    Vehicles vehicles;
    @JsonProperty("xmlns:xsi")
    public String getXmlnsXsi() {
        return this.xmlnsXsi; }
    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi; }
    String xmlnsXsi;
    @JsonProperty("services")
    public Services getServices() {
        return this.services; }
    public void setServices(Services services) {
        this.services = services; }
    Services services;
    @JsonProperty("problemType")
    public ProblemType getProblemType() {
        return this.problemType; }
    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType; }
    ProblemType problemType;
}

class Root{
    @JsonProperty("problem")
    public OutputModel getProblem() {
        return this.problem; }
    public void setProblem(OutputModel problem) {
        this.problem = problem; }
    OutputModel problem;
}