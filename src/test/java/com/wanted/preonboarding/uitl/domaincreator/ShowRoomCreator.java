package com.wanted.preonboarding.uitl.domaincreator;

import com.wanted.preonboarding.performance.domain.vo.PerformancePlace;

public class ShowRoomCreator {


	public PerformancePlace getShowRoom() {
		return PerformancePlace.create(3);
	}

}
