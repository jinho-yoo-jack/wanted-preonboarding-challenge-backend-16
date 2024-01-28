package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.application.exception.NotReservedStateException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Table(name = "performance_seat_info",
        uniqueConstraints = { @UniqueConstraint(name = "performance_seat_info_unique",
                columnNames = {"performance_id", "round", "line", "seat"})})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSeatInfo {

    public static final String ENABLE = "enable";
    public static final String DISABLE = "disable";
    public static final String NOT_RESERVED_MESSAGE_FORMAT = "고유 번호 [%d] 좌석은 예약 된 상태가 아닙니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private int gate;

    @Column(nullable = false)
    private char line;

    @Column(nullable = false)
    private int seat;

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'enable'")
    private String isReserve;

    @Builder
    private PerformanceSeatInfo(Long id, UUID performanceId, int round, int gate, char line, int seat, String isReserve) {
        this.id = id;
        this.performanceId = performanceId;
        this.round = round;
        this.gate = gate;
        this.line = line;
        this.seat = seat;
        this.isReserve = isReserve;
    }

    public boolean isPossibleReserve() {
        if (isReserve.equals(ENABLE)) {
            this.isReserve = DISABLE;
            return true;
        }

        return false;
    }

    public void cancel() {
        if (isReserve.equals(ENABLE)) {
            throw new NotReservedStateException(String.format(NOT_RESERVED_MESSAGE_FORMAT, id));
        }

        isReserve = ENABLE;
    }
}
