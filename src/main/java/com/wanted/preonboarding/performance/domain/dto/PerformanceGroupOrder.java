package com.wanted.preonboarding.performance.domain.dto;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import jakarta.validation.GroupSequence;

public interface PerformanceGroupOrder {
	
	interface selectEnablePerformanceInfoDetail{}
    
    @GroupSequence({selectEnablePerformanceInfoDetail.class, Default.class})
    interface AllLevelsValidation {}
}
