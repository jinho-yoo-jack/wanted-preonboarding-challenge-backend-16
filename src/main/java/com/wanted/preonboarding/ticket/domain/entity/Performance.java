package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Performance extends AuditInformation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    @Comment("공연/전시 이름")
    private String name;
    @Column(nullable = false)
    @Comment("가격")
    private int price;
    @Column(nullable = false)
    @Comment("회차")
    private int round;
    @Column(nullable = false)
    @Comment("NONE, CONCERT, EXHIBITION")
    private int type;
    @Column(nullable = false)
    @Comment("공연 일시")
    private Date start_date;
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    @Comment("예약 여부")
    private String isReserve;

}
