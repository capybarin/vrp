package com.diplom.vrp.utils;

import com.diplom.vrp.exceptions.ParameterIsNullOrLessThanZeroException;
import com.diplom.vrp.models.DepotModel;
import com.diplom.vrp.models.ServiceModel;
import com.diplom.vrp.models.VrpModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelValidator {

    private static Logger logger = LoggerFactory.getLogger(ModelValidator.class);

    public VrpModel validateModel(VrpModel model){
        int depotCount = 0;
        for (DepotModel depotModel: model.getDepots()) {
            if (depotModel.getVehicleType() == null || depotModel.getVehicleType().isEmpty()) {
                logger.error("An error occurred with \"depots[" + depotCount + "].vehicleType\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("depots[" + depotCount + "].vehicleType");
            }
            if (depotModel.getVehicleStartCoordinateX() == null) {
                logger.error("An error occurred with \"depots[" + depotCount + "].vehicleStartCoordinateX\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("depots[" + depotCount + "].vehicleStartCoordinateX");
            }
            if (depotModel.getVehicleStartCoordinateY() == null) {
                logger.error("An error occurred with \"depots[" + depotCount + "].vehicleStartCoordinateY\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("depots[" + depotCount + "].vehicleStartCoordinateY");
            }
            if (depotModel.getVehicleCapacity() == null || depotModel.getVehicleCapacity() <= 0) {
                logger.error("An error occurred with \"depots[" + depotCount + "].vehicleCapacity\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("depots[" + depotCount + "].vehicleCapacity");
            }
            if (depotModel.getCostPerWaitingTime() == null || depotModel.getCostPerWaitingTime() <= 0) {
                logger.error("An error occurred with \"depots[" + depotCount + "].costPerWaitingTime\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("depots[" + depotCount + "].costPerWaitingTime");
            }
            depotCount++;
        }
        int serviceCount = 0;
        for (ServiceModel serviceModel: model.getServices()) {
            if (serviceModel.getServiceId() == null || serviceModel.getServiceId().isEmpty()) {
                logger.error("An error occurred with \"services[" + serviceCount + "].serviceId\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + serviceCount + "].serviceId");
            }
            if (serviceModel.getEarliest() == null || serviceModel.getEarliest() <= 0) {
                logger.error("An error occurred with \"services[" + serviceCount + "].earliest\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + serviceCount + "].earliest");
            }
            if (serviceModel.getLatest() == null || serviceModel.getLatest() <= 0) {
                logger.error("An error occurred with \"services[" + serviceCount + "].latest\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + serviceCount + "].latest");
            }
            if (serviceModel.getDimensionValue() == null || serviceModel.getDimensionValue() <= 0) {
                logger.error("An error occurred with \"services[" + serviceCount + "].dimensionValue\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + serviceCount + "].dimensionValue");
            }
            if (serviceModel.getLocationX() == null) {
                logger.error("An error occurred with \"services[" + serviceCount + "].locationX\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + serviceCount + "].locationX");
            }
            if (serviceModel.getLocationY() == null) {
                logger.error("An error occurred with \"services[" + serviceCount + "].locationY\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + serviceCount + "].locationY");
            }
            serviceCount++;
        }
        return model;
    }
}
