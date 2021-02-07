package com.diplom.vrp.controllers;

import com.diplom.vrp.utils.MultipleTimeWindowSolution;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class VrpController {

    @PostMapping(path = "/solve", consumes = "application/json", produces = "application/json")
    public String solve(){
        return MultipleTimeWindowSolution.solve();
    }

    @PostMapping(path = "/ping", consumes = "application/json", produces = "application/json")
    public String postPing(){
        return "pong";
    }

    @GetMapping(path = "/ping", consumes = "application/json", produces = "application/json")
    public String getPing(){
        return "pong";
    }
}
