package com.arturo.projects.rabbitmq.rabbit_mq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/message/publish")
public class TriggerController {

    @PostMapping()
    public String postMethodName(@RequestBody String entity) {
        return entity;
    }
    
}