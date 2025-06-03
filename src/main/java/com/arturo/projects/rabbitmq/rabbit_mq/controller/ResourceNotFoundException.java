package com.arturo.projects.rabbitmq.rabbit_mq.controller;

public class ResourceNotFoundException extends Exception {

    public String getMessage() {
        return "Resource not found";
    }

}
