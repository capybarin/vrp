package com.diplom.vrp.models;

public class ServiceModel {

    private String serviceId;
    private Double earliest;
    private Double latest;
    private Integer dimensionValue;
    private Double locationX;
    private Double locationY;

    public ServiceModel(String serviceId, double earliest, double latest, int dimensionValue, double locationX, double locationY) {
        this.serviceId = serviceId;
        this.earliest = earliest;
        this.latest = latest;
        this.dimensionValue = dimensionValue;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public ServiceModel() {
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Double getEarliest() {
        return earliest;
    }

    public void setEarliest(Double earliest) {
        this.earliest = earliest;
    }

    public Double getLatest() {
        return latest;
    }

    public void setLatest(Double latest) {
        this.latest = latest;
    }

    public Integer getDimensionValue() {
        return dimensionValue;
    }

    public void setDimensionValue(Integer dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    @Override
    public String toString() {
        return "ServiceModel{" +
                "serviceId='" + serviceId + '\'' +
                ", earliest=" + earliest +
                ", latest=" + latest +
                ", dimensionValue=" + dimensionValue +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                '}';
    }
}
