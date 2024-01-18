package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.RequestNotification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@Comment("알림 정보")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("알림 설정 대상 공연/전시")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Comment("알림 설정 대상 공연/전시의 회차")
    private int round;

    @Comment("알림 수신 이메일 주소")
    private String email;

    @Comment("알림 발송 여부")
    private boolean isSent;

    public void markAsSent() {
        this.isSent = true;
    }

    public static Notification of(RequestNotification requestNotification, Performance performance) {
        return Notification.builder()
                .performance(performance)
                .round(requestNotification.round())
                .email(requestNotification.email())
                .isSent(false)
                .build();

    }

}
