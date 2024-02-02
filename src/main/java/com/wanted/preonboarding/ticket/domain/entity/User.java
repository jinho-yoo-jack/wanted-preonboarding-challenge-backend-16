package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.observer.Observer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements Observer {
    @Id
    private Long id;
    private String name;
    private String phoneNumber;
    private Long amount;
    private String membership;

    public void payAfterRemainingAmount(Long remainingAmount) {
        this.amount = remainingAmount;
    }

    @Override
    public void update(String message) {
        System.out.println(this.name + "님 신규 알람이 도착했습니다.");
        System.out.println(message);
    }
}