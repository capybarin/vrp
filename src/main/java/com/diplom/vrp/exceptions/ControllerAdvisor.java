package com.diplom.vrp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(ParameterIsNullOrLessThanZeroException.class)
    public ResponseEntity<Object> handleParameterIsNullOrLessThanZeroException(ParameterIsNullOrLessThanZeroException e){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());
        logger.error("ParameterIsNullOrLessThanZeroException " + e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResponseException.class)
    public ResponseEntity<Object> handleEmptyResponseException(EmptyResponseException e){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());
        logger.error("ParameterIsNullOrLessThanZeroException " + e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
