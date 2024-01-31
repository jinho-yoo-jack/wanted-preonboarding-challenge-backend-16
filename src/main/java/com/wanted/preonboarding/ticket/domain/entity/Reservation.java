package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "reservation", uniqueConstraints = {
        @UniqueConstraint(name = "reservation_round_row_seat", columnNames = {"performance_id", "round", "line", "seat"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID performanceId;

    @Embedded
    private UserInfo userInfo;

    @Embedded
    private SeatInfo seatInfo;

    public boolean compareUserInfo(UserInfo userInfo) {
        return this.userInfo.getName().equals(userInfo.getName())
                && this.userInfo.getPhoneNumber().equals(userInfo.getPhoneNumber());
    }
}
