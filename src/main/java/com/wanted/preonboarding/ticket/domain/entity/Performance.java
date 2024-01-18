package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "performance", uniqueConstraints = {
        @UniqueConstraint(name = "performance_id_uk", columnNames = "id"),
        @UniqueConstraint(name = "performance_round_uk", columnNames = "round")
})
public class Performance extends BaseEntity {

    @Id
    @Comment("공연 정보 ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", type = UuidGenerator.class)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Comment("공연명")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment("가격")
    @Column(name = "price", nullable = false)
    private Integer price;

    @Comment("회차 정보")
    @Column(name = "round", nullable = false)
    private Integer round;

    @Comment("공연 구분 - 콘서트, 전시회, 기타")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PerformanceType type;

    @Comment("공연 시작일")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Comment("예약 가능 여부")
    @Column(name = "is_reserve", nullable = false, columnDefinition = "varchar(255) default 'disable'")
    private String isReserve;

}
