package com.wanted.preonboarding.uitl.domaincreator;

import com.wanted.preonboarding.performance.domain.vo.Gate;

public class ShowRoomCreator {


	public Gate getShowRoom() {
		return Gate.create(3);
	}

}
