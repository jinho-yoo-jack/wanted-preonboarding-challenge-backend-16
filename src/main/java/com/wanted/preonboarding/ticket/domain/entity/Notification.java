package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class Notification {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, name = "performance_id")
    private UUID performanceId;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    private Boolean isSend;

    public static Notification of(UUID performanceId, String phoneNumber) {
        Notification notification = new Notification();
        notification.performanceId = performanceId;
        notification.phoneNumber = phoneNumber;
        notification.isSend = false;
        return notification;
    }

    public void chagneIsSendTrue() {
        this.isSend = true;
    }
}
