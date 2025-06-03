package com.arturo.projects.rabbitmq.rabbit_mq.controller;

public class EntityNotFoundException extends Exception{

    public String getMessage() {
        return "Entity not found";
    }

}
