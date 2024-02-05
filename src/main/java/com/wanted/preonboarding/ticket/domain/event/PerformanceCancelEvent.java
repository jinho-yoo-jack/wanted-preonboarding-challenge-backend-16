package com.wanted.preonboarding.ticket.domain.event;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

public record PerformanceCancelEvent(Performance performance) {
}
