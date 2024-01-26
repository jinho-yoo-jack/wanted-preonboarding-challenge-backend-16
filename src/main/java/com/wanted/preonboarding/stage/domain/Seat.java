package com.wanted.preonboarding.stage.domain;

import com.wanted.preonboarding.stage.domain.constant.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    private Long id;
    @Column(name = "seat_name",length = 30,nullable = false)
    private String name; // 좌석 명칭
    @Column(length = 10)
    private String grade; // 등급
    @Enumerated(value = EnumType.STRING)
    private SeatStatus seatStatus;
    private Integer price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id",nullable = false)
    private Stage stage;

    public void setStage(Stage stage) {
        if(this.stage != null) {
            this.stage.getSeats().remove(this);
        }
        this.stage = stage;
        this.stage.getSeats().add(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
