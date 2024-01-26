package com.wanted.preonboarding.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
@Embeddable
@NoArgsConstructor
public class PerformanceId {

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID value;
}
