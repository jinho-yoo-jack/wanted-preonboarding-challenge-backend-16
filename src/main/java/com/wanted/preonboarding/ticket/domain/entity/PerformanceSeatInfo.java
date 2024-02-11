package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.code.ActiveType;
import com.wanted.preonboarding.ticket.domain.code.converter.ActiveTypeConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "performance_seat_info")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerformanceSeatInfo {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(referencedColumnName = "id", name = "performance_id"),
            @JoinColumn(referencedColumnName = "round", name = "round")
    })
    private Performance performance;

    @Column(name = "line", unique = true)
    @EqualsAndHashCode.Include
    private String line;

    @Column(name = "seat", unique = true)
    @EqualsAndHashCode.Include
    private int seat;

    @Column(name = "gate")
    private int gate;

    @Column(name = "is_reserve")
    @Convert(converter = ActiveTypeConverter.class)
    private ActiveType isReserve;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
