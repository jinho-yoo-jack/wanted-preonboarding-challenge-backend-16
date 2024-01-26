package com.wanted.preonboarding.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class ReservableEntity extends DefaultEntity {

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String reserveState;

    public boolean isReserved() {
        return this.reserveState.equals("disable");
    }

    public void disableReservation() {
        this.reserveState = "disable";
    }

    public void enableReservation() {
        this.reserveState = "enable";
    }
}
