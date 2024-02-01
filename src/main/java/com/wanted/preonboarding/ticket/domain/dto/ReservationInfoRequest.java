package com.wanted.preonboarding.ticket.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ReservationInfoRequest {
    @NotBlank(message = "name should not be empty")
    String name;
    @NotBlank(message = "phone number should not be empty")
    String phoneNumber;
}
