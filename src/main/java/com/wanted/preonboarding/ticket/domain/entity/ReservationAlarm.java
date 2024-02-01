package com.wanted.preonboarding.ticket.domain.entity;


import com.wanted.preonboarding.ticket.domain.dto.SubscriberPerformanceRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ReservationAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID performanceId;
    private String receiver;

    @Builder
    public ReservationAlarm(UUID performanceId, String receiver) {
        this.performanceId = performanceId;
        this.receiver = receiver;
    }

    public static ReservationAlarm of(SubscriberPerformanceRequestDto requestDto){
        return ReservationAlarm.builder()
            .performanceId(requestDto.getPerformanceId())
            .receiver(requestDto.getReceiver())
            .build();

    }



}
