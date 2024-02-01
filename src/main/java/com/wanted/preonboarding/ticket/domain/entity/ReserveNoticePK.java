package com.wanted.preonboarding.ticket.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveNoticePK implements Serializable {

    private String userName;
    private UUID performanceId;
}
