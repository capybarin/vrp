package com.diplom.vrp.controllers;

import com.diplom.vrp.utils.MultipleTimeWindowSolution;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VrpController {

    @PostMapping(path = "/solve", consumes = "application/json", produces = "application/json")
    public String solve(){
        return MultipleTimeWindowSolution.solve();
    }
}
