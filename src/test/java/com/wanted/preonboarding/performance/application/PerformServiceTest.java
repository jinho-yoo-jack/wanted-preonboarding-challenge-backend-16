package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceResponse;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@DisplayName("서비스: 공연 및 전시 - PerformService")
public class PerformServiceTest extends ServiceTest {

	@Autowired
	private PerformService performService;

	@Autowired
	private PerformAdminService performAdminService;



	@Test
	public void 예약_가능한_공연_및_전시_정보_목록을_조회할_수_있다() {
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		performAdminService.register(performanceRequest);

		//when
		List<PerformanceResponse> response = performService.getAllPerformanceInfoList(true);

		//then
		AssertCluster.performanceAssert(performanceRequest, response.get(0));
	}

	@Test
	public void 예약_불가능한_공연_및_전시_정보_목록을_조회할_수_있다() {
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		request.setReserve(false);
		PerformanceRequest performanceRequest = request.create();
		performAdminService.register(performanceRequest);

		//when
		List<PerformanceResponse> response = performService.getAllPerformanceInfoList(false);

		//then
		AssertCluster.performanceAssert(performanceRequest, response.get(0));
	}

	@Test
	public void 공연_및_전시_정보_상세를_조회할_수_있다() {
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		UUID performId = performAdminService.register(performanceRequest);

		//when
		PerformanceResponse response = performService.getPerformanceInfoDetail(performId);

		//then
		AssertCluster.performanceAssert(performanceRequest, response);
	}

}
