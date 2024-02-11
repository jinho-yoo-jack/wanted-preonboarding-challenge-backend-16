package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerformanceId implements Serializable {

    @Serial
    private static final long serialVersionUID = -4136920652482631030L;

    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "round", unique = true)
    @EqualsAndHashCode.Include
    private int round;
}
