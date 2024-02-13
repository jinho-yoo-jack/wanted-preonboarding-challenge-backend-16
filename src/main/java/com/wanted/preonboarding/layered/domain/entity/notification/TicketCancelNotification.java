package com.wanted.preonboarding.layered.domain.entity.notification;

import com.wanted.preonboarding.layered.domain.dto.NotificationRegister;
import com.wanted.preonboarding.layered.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at <> IS NULL")
public class TicketCancelNotification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false, name = "email_address")
    private String emailAddress;

    public static TicketCancelNotification of(NotificationRegister register) {
        return TicketCancelNotification.builder()
            .performanceId(register.getPerformanceId())
            .name(register.getUserName())
            .phoneNumber(register.getPhoneNumber())
            .build();
    }
}
