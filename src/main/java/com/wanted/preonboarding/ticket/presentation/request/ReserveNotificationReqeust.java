package com.wanted.preonboarding.ticket.presentation.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ReserveNotificationReqeust {
    private UUID performanceId;
    private String phoneNumber;
}
