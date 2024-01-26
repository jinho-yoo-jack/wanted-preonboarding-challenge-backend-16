package com.wanted.preonboarding.stage.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    private Long id;
    @Column(name = "stage_name", length = 30, nullable = false)
    private String name;
    @Column(length = 50,nullable = false)
    private String address;
    @OneToMany(mappedBy = "stage",cascade = CascadeType.ALL)
    private List<Seat> seats;

    public Stage() {
        this.seats = new ArrayList<>();
    }

    @Builder
    public Stage(String name,String address, List<Seat> seats) {
        this.name = name;
        this.address = address;
        this.seats = new ArrayList<>();
        if(seats != null) {
            seats.forEach(this::addSeat);
        }
    }

    public void addSeat(Seat seat) {
        seat.setStage(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stage stage = (Stage) o;
        return Objects.equals(id, stage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
