package com.wanted.preonboarding.performance.domain.creator;

import com.wanted.preonboarding.performance.domain.vo.Gate;

public class ShowRoomCreator {


	public Gate getShowRoom() {
		return Gate.create(3);
	}

}
