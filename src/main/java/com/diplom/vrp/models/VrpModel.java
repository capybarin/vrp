package com.diplom.vrp.models;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


public class VrpModel {

    @ApiModelProperty(notes = "List of depots", required = true)
    private List<DepotModel> depots;
    @ApiModelProperty(notes = "List of location to serve", required = true)
    private List<ServiceModel> services;

    public List<DepotModel> getDepots() {
        return depots;
    }

    public void setDepots(List<DepotModel> depots) {
        this.depots = depots;
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
                "depots=" + depots.toString() +
                ", services=" + services.toString() +
                '}';
    }
}
