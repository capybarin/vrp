package com.diplom.vrp.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@Api(value = "Ping controller")
public class PingController {

    private static Logger logger = LoggerFactory.getLogger(PingController.class);


    @ApiOperation(value = "Check if server is up")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "A monkey is trying to deal with this situation"),
            @ApiResponse(code = 200, message = "pong")
    })
    @PostMapping(path = "/ping", produces = "application/json")
    public String isAlivePost(){
        logger.info("Entering POST /ping endpoint");
        return "pong";
    }

    @ApiOperation(value = "Check if server is up")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "A monkey is trying to deal with this situation"),
            @ApiResponse(code = 200, message = "pong")
    })
    @GetMapping(path = "/ping", produces = "application/json")
    public String isAliveGet(){
        logger.info("Entering GET /ping endpoint");
        return "pong";
    }
    @GetMapping("/listHeaders")
    public ResponseEntity<String> listAllHeaders(
            @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            logger.info(String.format("Header '%s' = %s", key, value));
        });

        return new ResponseEntity<String>(
                String.format("Listed %d headers", headers.size()), HttpStatus.OK);
    }
}
