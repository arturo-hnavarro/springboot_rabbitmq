package com.arturo.projects.rabbitmq.rabbit_mq.domain;

public class Order {

    private Long total;
    private String clientId;

    
    public Order() {
    }

    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    
}