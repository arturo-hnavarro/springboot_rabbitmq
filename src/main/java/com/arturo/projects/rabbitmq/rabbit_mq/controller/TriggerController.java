package com.arturo.projects.rabbitmq.rabbit_mq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/message/publish")
public class TriggerController {

    @PostMapping()
    public String postMethodName(@RequestBody String entity) {
        return entity;
    }

    @GetMapping("/errors/{error}")
    public String handleErrors(@PathVariable String error) throws ResourceNotFoundException, EntityNotFoundException, 
        IllegalArgumentException, Exception {
        if(error.equals("ResourceNotFoundException"))
            throw new ResourceNotFoundException();

        if(error.equals("EntityNotFoundException"))
            throw new EntityNotFoundException();

        if(error.equals("IllegalArgumentException"))
            throw new IllegalArgumentException("IllegalArgumentException");

        if(error.equals("HandleGeneralException"))
            throw new Exception("HandleGeneralException");

        return "";
    }


    
}