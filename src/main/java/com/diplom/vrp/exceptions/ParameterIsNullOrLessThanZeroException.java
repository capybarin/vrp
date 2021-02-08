package com.diplom.vrp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ParameterIsNullOrLessThanZeroException extends RuntimeException {
    public ParameterIsNullOrLessThanZeroException(String param){
        super(param + " is null or less than 0");
    }
}
