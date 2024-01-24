package com.wanted.preonboarding.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class ReservableEntity extends DefaultEntity {

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;

    public boolean isReserved() {
        return this.isReserve.equals("disable");
    }

    public void disableReservation() {
        this.isReserve = "disable";
    }

    public void enableReservation() {
        this.isReserve = "enable";
    }
}
