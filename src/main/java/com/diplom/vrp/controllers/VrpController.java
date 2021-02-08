package com.diplom.vrp.controllers;

import com.diplom.vrp.models.VrpModel;
import com.diplom.vrp.utils.MultipleTimeWindowSolution;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class VrpController {

    @PostMapping(path = "/solve", consumes = "application/json", produces = "application/json")
    public String solve(@RequestBody VrpModel model){
        return MultipleTimeWindowSolution.solve(model);
    }

    @PostMapping(path = "/ping", produces = "application/json")
    public String postPing(){
        return "pong";
    }

    @GetMapping(path = "/ping", produces = "application/json")
    public String getPing(){
        return "pong";
    }

}
