package com.diplom.vrp.controllers;

import com.diplom.vrp.models.OutputModel;
import com.diplom.vrp.models.VrpModel;
import com.diplom.vrp.utils.MultipleTimeWindowSolution;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@Api(value = "Main controller")
public class VrpController {

    private static Logger logger = LoggerFactory.getLogger(VrpController.class);

    @ApiOperation(value = "Solve the VRP with TW", response = OutputModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 422, message = "`ParamName` is null or less than 0"),
            @ApiResponse(code = 200, message = "A JSON representation of optimal routes")
    })
    @PostMapping(path = "/solve", consumes = "application/json", produces = "application/json")
    public String solve(@RequestBody VrpModel model){
        logger.info("Entering /solve endpoint");
        return MultipleTimeWindowSolution.solve(model);
    }

}
