package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.AssertCluster;
import com.wanted.preonboarding.ticket.PerformanceRequestFactory;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceResponse;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("공연 및 전시 조회 서비스 테스트")
public class TicketSellerTest {

	@Autowired
	private TicketSeller ticketSeller;

	@Autowired
	private PerformanceAdminService performanceAdminService;

	@Autowired
	private PerformanceRepository performanceRepository;

	@BeforeEach
	public void clear(){
		performanceRepository.deleteAll();
	}

	@Test
	public void 예약_가능한_공연_및_전시_정보_목록을_조회할_수_있다() {
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		performanceAdminService.register(performanceRequest);

		//when
		List<PerformanceResponse> response = ticketSeller.getAllPerformanceInfoList(true);

		//then
		AssertCluster.performanceAssert(performanceRequest, response.get(0));
	}

	@Test
	public void 예약_불가능한_공연_및_전시_정보_목록을_조회할_수_있다() {
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		request.setReserve(false);
		PerformanceRequest performanceRequest = request.create();
		performanceAdminService.register(performanceRequest);

		//when
		List<PerformanceResponse> response = ticketSeller.getAllPerformanceInfoList(false);

		//then
		AssertCluster.performanceAssert(performanceRequest, response.get(0));
	}

	@Test
	public void 공연_및_전시_정보_상세를_조회할_수_있다() {
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		performanceAdminService.register(performanceRequest);

		//when
		PerformanceResponse response = ticketSeller.getPerformanceInfoDetail(performanceRequest.name());

		//then
		AssertCluster.performanceAssert(performanceRequest, response);
	}

}
