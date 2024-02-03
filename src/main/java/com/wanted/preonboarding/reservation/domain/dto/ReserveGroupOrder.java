package com.wanted.preonboarding.reservation.domain.dto;

import jakarta.validation.GroupSequence;

public interface ReserveGroupOrder {
	interface selectReserveInfo{}
    interface reservation {}
    
    @GroupSequence({reservation.class,selectReserveInfo.class })
    interface AllLevelsValidation {}
}
