package com.diplom.vrp.controllers;

import com.diplom.vrp.models.VrpModel;
import com.diplom.vrp.utils.MultipleTimeWindowSolution;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@Api(value = "Main controller")
public class VrpController {

    @ApiOperation(value = "Solve the VRP with TW")
    @ApiResponses(value = {
            @ApiResponse(code = 422, message = "`ParamName` is null or less than 0"),
            @ApiResponse(code = 200, message = "A JSON representation of optimal routes")
    })
    @PostMapping(path = "/solve", consumes = "application/json", produces = "application/json")
    public String solve(@RequestBody VrpModel model){
        return MultipleTimeWindowSolution.solve(model);
    }

    @ApiOperation(value = "Check if server is up")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "pong")
    })
    @PostMapping(path = "/ping", produces = "application/json")
    public String postPing(){
        return "pong";
    }

    @ApiOperation(value = "Check if server is up")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "pong")
    })
    @GetMapping(path = "/ping", produces = "application/json")
    public String getPing(){
        return "pong";
    }

}
