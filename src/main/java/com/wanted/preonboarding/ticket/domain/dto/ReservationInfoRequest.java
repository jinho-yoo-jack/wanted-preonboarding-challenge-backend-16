package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
public class ReservationInfoRequest {
    @NotBlank(message = "name should not be empty")
    String name;
    @NotBlank(message = "phone number should not be empty")
    String phoneNumber;
}
