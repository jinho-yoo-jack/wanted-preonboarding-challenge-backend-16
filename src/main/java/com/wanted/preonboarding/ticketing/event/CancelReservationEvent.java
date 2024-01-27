package com.wanted.preonboarding.ticketing.event;

import java.util.List;

public record CancelReservationEvent(List<String> emails, Long performanceSeatInfoId) {
}
