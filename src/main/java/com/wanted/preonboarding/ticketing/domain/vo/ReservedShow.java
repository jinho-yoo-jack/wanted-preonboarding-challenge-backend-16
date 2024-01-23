package com.wanted.preonboarding.ticketing.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReservedShow {
    private Long showId;
    private int session;
}
