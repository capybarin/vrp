package com.diplom.vrp.models;

import io.swagger.annotations.ApiModelProperty;

public class DepotModel {

    @ApiModelProperty(notes = "Vehicle type")
    private String vehicleType;
    @ApiModelProperty(notes = "X coordinate of the vehicle's start location", required = true)
    private Double vehicleStartCoordinateX;
    @ApiModelProperty(notes = "Y coordinate of the vehicle's start location", required = true)
    private Double vehicleStartCoordinateY;
    @ApiModelProperty(notes = "Vehicle capacity", required = true)
    private Integer vehicleCapacity;
    @ApiModelProperty(notes = "Сost per waiting time unit, for instance € per second", required = true)
    private Double costPerWaitingTime;

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

    public Integer getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(Integer vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

    public Double getCostPerWaitingTime() {
        return costPerWaitingTime;
    }

    public void setCostPerWaitingTime(Double costPerWaitingTime) {
        this.costPerWaitingTime = costPerWaitingTime;
    }

    @Override
    public String toString() {
        return "DepotModel{" +
                "vehicleType='" + vehicleType + '\'' +
                ", vehicleStartCoordinateX=" + vehicleStartCoordinateX +
                ", vehicleStartCoordinateY=" + vehicleStartCoordinateY +
                ", vehicleCapacity=" + vehicleCapacity +
                ", costPerWaitingTime=" + costPerWaitingTime +
                '}';
    }
}
