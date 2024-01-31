package com.wanted.preonboarding.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class ReservableEntity extends DefaultEntity {

    private static final String ENABLE = "enable";
    private static final String DISABLE = "disable";

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String reserveState;

    public boolean isReserved() {
        return this.reserveState.equals(DISABLE);
    }

    public boolean canReserve() {
        return this.reserveState.equals(ENABLE);
    }

    public void disableReservation() {
        this.reserveState = DISABLE;
    }

    public void enableReservation() {
        this.reserveState = ENABLE;
    }
}
