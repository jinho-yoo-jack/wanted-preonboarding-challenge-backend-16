package com.wanted.preonboarding.ticketing.domain.entity;

import com.wanted.preonboarding.ticketing.domain.dto.response.CreateAlarmResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Getter
public class Alarm extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("알람 Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", columnDefinition = "BINARY(16)", nullable = false)
    @Comment("공연전시ID")
    private Performance performance;

    @Column(nullable = false)
    @Comment("알람 신청자명")
    private String name;

    @Column(nullable = false)
    @Comment("휴대전화")
    private String phoneNumber;

    @Column(nullable = false)
    @Comment("이메일")
    private String email;

    public CreateAlarmResponse toCreateAlarmResponse() {
        return CreateAlarmResponse.builder()
                .alarmId(this.id)
                .performanceId(this.performance.getId())
                .customerName(this.name)
                .phoneNumber(this.phoneNumber)
                .startedTime(this.getCreatedAt())
                .build();
    }
}
