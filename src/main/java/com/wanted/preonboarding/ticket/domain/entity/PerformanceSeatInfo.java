package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Entity
@ToString
public class PerformanceSeatInfo {
    @Id
    private Long id;
    private int round;
    private int gate;
    private char line;
    private int seat;
    private String isReserve;
    private UUID performanceId;

    public void changeDisableReserveStatus() {
        this.isReserve = "disable";
    }

    public void changeEnableReserveStatus() {
        this.isReserve = "enable";
    }
}
