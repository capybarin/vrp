package com.diplom.vrp.utils;

import com.diplom.vrp.exceptions.ParameterIsNullOrLessThanZeroException;
import com.diplom.vrp.models.ServiceModel;
import com.diplom.vrp.models.VrpModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelValidator {

    private static Logger logger = LoggerFactory.getLogger(ModelValidator.class);

    public VrpModel validateModel(VrpModel model){
        if (model.getVehicleType() == null || model.getVehicleType().equals("")) {
            logger.warn("Vehicle name is not provided. Setting default name");
            model.setVehicleType("Vehicle");
        }
        if (model.getCostPerWaitingTime() == null || model.getCostPerWaitingTime() <= 0) {
            logger.error("An error occurred with \"costPerWaitingTime\" parameter");
            throw new ParameterIsNullOrLessThanZeroException("costPerWaitingTime");
        }
        if (model.getVehicleCapacity() == null || model.getVehicleCapacity() <= 0){
            logger.error("An error occurred with \"vehicleCapacity\" parameter");
            throw new ParameterIsNullOrLessThanZeroException("vehicleCapacity");
        }
        if (model.getVehicleStartCoordinateX() == null || model.getVehicleStartCoordinateX() <= 0) {
            logger.error("An error occurred with \"vehicleStartCoordinateX\" parameter");
            throw new ParameterIsNullOrLessThanZeroException("vehicleStartCoordinateX");
        }
        if (model.getVehicleStartCoordinateY() == null || model.getVehicleStartCoordinateY() <= 0) {
            logger.error("An error occurred with \"vehicleStartCoordinateY\" parameter");
            throw new ParameterIsNullOrLessThanZeroException("vehicleStartCoordinateY");
        }
        int count = 0;
        for (ServiceModel serviceModel: model.getServices()) {
            if (serviceModel.getServiceId() == null || serviceModel.getServiceId().isEmpty()) {
                logger.error("An error occurred with \"services[" + count + "].serviceId\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + count + "].serviceId");
            }
            if (serviceModel.getEarliest() == null || serviceModel.getEarliest() <= 0) {
                logger.error("An error occurred with \"services[" + count + "].earliest\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + count + "].earliest");
            }
            if (serviceModel.getLatest() == null || serviceModel.getLatest() <= 0) {
                logger.error("An error occurred with \"services[" + count + "].latest\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + count + "].latest");
            }
            if (serviceModel.getDimensionValue() == null || serviceModel.getDimensionValue() <= 0) {
                logger.error("An error occurred with \"services[" + count + "].dimensionValue\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + count + "].dimensionValue");
            }
            if (serviceModel.getLocationX() == null || serviceModel.getLocationX() <= 0) {
                logger.error("An error occurred with \"services[" + count + "].locationX\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + count + "].locationX");
            }
            if (serviceModel.getLocationY() == null || serviceModel.getLocationY() <= 0) {
                logger.error("An error occurred with \"services[" + count + "].locationY\" parameter");
                throw new ParameterIsNullOrLessThanZeroException("services[" + count + "].locationY");
            }
            count++;
        }
        return model;
    }
}
