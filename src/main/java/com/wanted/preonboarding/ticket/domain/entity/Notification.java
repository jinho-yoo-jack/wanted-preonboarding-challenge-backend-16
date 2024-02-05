package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.request.NotificationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationFindResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Table
@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends AuditInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Comment("알림 신청자 이름")
    private String name;

    @Column(name = "phone_number", nullable = false)
    @Comment("신청자 휴대전화 번호")
    private String phoneNumber;

    @Column(nullable = false)
    @Comment("신청자 이메일 주소")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false, columnDefinition = "BINARY(16)")
    @Comment("공연전시ID")
    private Performance performance;

    public static Notification of(NotificationCreateRequest request, Performance performance) {
        return Notification.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .performance(performance)
                .build();
    }

    public NotificationCreateResponse toNotificationCreateResponse() {
        return NotificationCreateResponse.builder()
                .name(this.name)
                .performanceId(this.performance.getId())
                .build();
    }

    public NotificationFindResponse toNotificationFindResponse() {
        return NotificationFindResponse.builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .performanceId(this.performance.getId())
                .build();
    }
}
