package com.wanted.preonboarding.performance.domain.creator;

import com.wanted.preonboarding.performance.domain.PerformanceShowroom;

public class ShowRoomCreator {


	public PerformanceShowroom getShowRoom() {
		return PerformanceShowroom.create(3);
	}

}
