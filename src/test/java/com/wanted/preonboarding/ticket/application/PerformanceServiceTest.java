package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.ticket.AssertCluster;
import com.wanted.preonboarding.ticket.ShowingRequestFactory;
import com.wanted.preonboarding.ticket.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.presentation.dto.PerformanceResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@DisplayName("공연 및 전시 조회 서비스 테스트")
public class PerformanceServiceTest extends ServiceTest {

	@Autowired
	private PerformanceService performanceService;

	@Autowired
	private ShowingAdminService showingAdminService;



	@Test
	public void 예약_가능한_공연_및_전시_정보_목록을_조회할_수_있다() {
		//given
		ShowingRequestFactory request = new ShowingRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		showingAdminService.register(performanceRequest);

		//when
		List<PerformanceResponse> response = performanceService.getAllPerformanceInfoList(true);

		//then
		AssertCluster.performanceAssert(performanceRequest, response.get(0));
	}

	@Test
	public void 예약_불가능한_공연_및_전시_정보_목록을_조회할_수_있다() {
		//given
		ShowingRequestFactory request = new ShowingRequestFactory();
		request.setReserve(false);
		PerformanceRequest performanceRequest = request.create();
		showingAdminService.register(performanceRequest);

		//when
		List<PerformanceResponse> response = performanceService.getAllPerformanceInfoList(false);

		//then
		AssertCluster.performanceAssert(performanceRequest, response.get(0));
	}

	@Test
	public void 공연_및_전시_정보_상세를_조회할_수_있다() {
		//given
		ShowingRequestFactory request = new ShowingRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		showingAdminService.register(performanceRequest);

		//when
		PerformanceResponse response = performanceService.getPerformanceInfoDetail(performanceRequest.name());

		//then
		AssertCluster.performanceAssert(performanceRequest, response);
	}

}
