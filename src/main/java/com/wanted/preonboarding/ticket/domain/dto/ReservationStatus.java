package com.wanted.preonboarding.ticket.domain.dto;

public enum ReservationStatus {
    ENABLE("enable"),
    DISABLE("disable");

    private final String status;

    ReservationStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
