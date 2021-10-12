package com.dicka.rnd.retrymecanism.controller;

import com.dicka.rnd.retrymecanism.service.CheckStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/check")
public class CheckStatusController {

    @Autowired
    private CheckStatusService checkStatusService;

    @GetMapping(value = "/{id}")
    public String getCheckStatus(@PathVariable("id")String id){
        return checkStatusService.checkAvailableStatus(id);
    }
}
