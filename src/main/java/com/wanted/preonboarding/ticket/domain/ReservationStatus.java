package com.wanted.preonboarding.ticket.domain;

public enum ReservationStatus {
    PENDING("Pending"),
    REJECTED("Rejected"),
    APPROVED("Approved");
    private final String status;


    ReservationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
