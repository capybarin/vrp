package com.diplom.vrp.controllers;

import com.diplom.vrp.models.OutputModel;
import com.diplom.vrp.models.VrpModel;
import com.diplom.vrp.utils.MultipleTimeWindowSolution;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@Api(value = "Main controller")
public class VrpController {

    private static Logger logger = LoggerFactory.getLogger(VrpController.class);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "A JSON representation of vehicle's parameters and services data")
    })
    @ApiOperation(value = "Solve the VRP with TW", response = OutputModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "A monkey is trying to deal with this situation"),
            @ApiResponse(code = 422, message = "`ParamName` is null or less than 0"),
            @ApiResponse(code = 200, message = "A JSON representation of optimal routes")
    })
    @PostMapping(path = "/solve", consumes = "application/json", produces = "application/json")
    public String solve(@RequestBody VrpModel model){
        logger.info("Entering /solve endpoint");
        MultipleTimeWindowSolution solution = new MultipleTimeWindowSolution();
        return solution.solve(model);
    }

}
