package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ResponseAlarmDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String memberName;
    private Performance performance;

    private Alarm(String memberName,Performance performance) {
        this.memberName = memberName;
        this.performance = performance;
    }

    public static Alarm of(String memberName,Performance performance){
        return new Alarm(memberName,performance);
    }

    public ResponseAlarmDto toResponseDto(){
        return ResponseAlarmDto.builder()
                .receiver(this.memberName)
                .performanceId(this.performance.getId())
                .performanceName(this.performance.getName())
                .startDate(this.performance.getStart_date())
                .build();
    }
}