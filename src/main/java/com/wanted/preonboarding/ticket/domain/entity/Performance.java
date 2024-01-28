package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.request.RegisterPerformance;
import com.wanted.preonboarding.ticket.domain.enums.PerformanceType;
import com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@Comment("공연/전시 정보")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "performance", uniqueConstraints = {
        @UniqueConstraint(name = "performance_id_uk", columnNames = {"id", "round"}),
})
public class Performance extends BaseEntity {

    @Id
    @Comment("공연/전시 정보 ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)") // BINARY(16)이 아니면, 공백이 포함되어 오류 발생 가능성
    private UUID id;

    @Comment("공연/전시 이름")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment("가격")
    @Column(name = "price", nullable = false)
    private Integer price;

    @Comment("회차")
    @Column(name = "round", nullable = false)
    private Integer round;

    @Comment("공연 구분 - 콘서트, 전시회, 기타")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private PerformanceType type;

    @Comment("공연 일시")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Comment("예약 가능 여부")
    @Enumerated(EnumType.STRING)
    @Column(name = "is_reserve", nullable = false, columnDefinition = "varchar(20) default 'DISABLED'")
    private ReservationAvailability isReserve;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL)
    private List<PerformanceSeatInfo> performanceSeatInfoList = new ArrayList<>();

    public static Performance of(RegisterPerformance request) {
        return Performance.builder()
                .name(request.name())
                .price(request.price())
                .round(request.round())
                .type(PerformanceType.valueOf(request.type()))
                .startDate(LocalDateTime.parse(request.startDate()))
                .isReserve(ReservationAvailability.DISABLED)
                .build();
    }
    public void setPerformanceSeatInfoList(List<PerformanceSeatInfo> performanceSeatInfoList) {
        this.performanceSeatInfoList = performanceSeatInfoList;
    }

}
