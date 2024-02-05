package com.wanted.preonboarding.ticket.controller.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketHolderModel {
    private String userName;
    private String phoneNumber;
}
