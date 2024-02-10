package com.wanted.preonboarding.ticket.domain.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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

    @OneToOne
    @JoinColumn(name = "performance_seat_info_id")
    private PerformanceSeatInfo performanceSeatInfo;

    private int round;

    private char line;

    private int seat;

    public WaitingList of(Performance performance) {
        WaitingList waitingList = new WaitingList();
        waitingList.name = performance.getName();
        waitingList.phoneNumber = performance.
    }
}
