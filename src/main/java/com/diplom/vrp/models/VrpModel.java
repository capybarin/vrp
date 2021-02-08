package com.diplom.vrp.models;

import java.util.List;


public class VrpModel {

    private String vehicleType;
    private Double vehicleStartCoordinateX;
    private Double vehicleStartCoordinateY;
    private Double vehicleCapacity;
    private Double costPerWaitingTime;
    private List<ServiceModel> services;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getVehicleStartCoordinateX() {
        return vehicleStartCoordinateX;
    }

    public void setVehicleStartCoordinateX(Double vehicleStartCoordinateX) {
        this.vehicleStartCoordinateX = vehicleStartCoordinateX;
    }

    public Double getVehicleStartCoordinateY() {
        return vehicleStartCoordinateY;
    }

    public void setVehicleStartCoordinateY(Double vehicleStartCoordinateY) {
        this.vehicleStartCoordinateY = vehicleStartCoordinateY;
    }

    public Double getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(Double vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

    public Double getCostPerWaitingTime() {
        return costPerWaitingTime;
    }

    public void setCostPerWaitingTime(Double costPerWaitingTime) {
        this.costPerWaitingTime = costPerWaitingTime;
    }

    public List<ServiceModel> getServices() {
        return services;
    }

    public void setServices(List<ServiceModel> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "VrpModel{" +
                "vehicleType='" + vehicleType + '\'' +
                ", vehicleStartCoordinateX=" + vehicleStartCoordinateX +
                ", vehicleStartCoordinateY=" + vehicleStartCoordinateY +
                ", vehicleCapacity=" + vehicleCapacity +
                ", costPerWaitingTime=" + costPerWaitingTime +
                ", services=" + services +
                '}';
    }
}
