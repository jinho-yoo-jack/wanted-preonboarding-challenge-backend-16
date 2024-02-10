package com.wanted.preonboarding.ticket.domain.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.wanted.preonboarding.ticket.domain.dto.request.WaitReservationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class WaitingList {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "waiting_list_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    private UUID performanceId;
    private int round;
    private char line;
    private int seat;

    public static WaitingList of(WaitReservationRequest request, PerformanceSeatInfo entity) {
        WaitingList waitingList = new WaitingList();
        waitingList.name = request.getName();
        waitingList.phoneNumber = request.getPhoneNumber();
        waitingList.performanceId = entity.getPerformance().getId();
        waitingList.round = request.getRound();
        waitingList.line = request.getLine();
        waitingList.seat = request.getSeat();
        return waitingList;
    }
}
