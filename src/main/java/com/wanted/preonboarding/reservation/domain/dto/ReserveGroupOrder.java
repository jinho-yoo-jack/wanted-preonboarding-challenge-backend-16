package com.wanted.preonboarding.reservation.domain.dto;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import jakarta.validation.GroupSequence;

public interface ReserveGroupOrder {
	interface selectReserveInfo{}
    interface reservation {}
    
    @GroupSequence({selectReserveInfo.class, reservation.class, Default.class})
    interface AllLevelsValidation {}
}
