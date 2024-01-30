package com.wanted.preonboarding.performance.presentation;

import com.wanted.preonboarding.ApiTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.ShowingRequestFactory;
import com.wanted.preonboarding.performance.application.ShowingAdminService;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@DisplayName("공연 예약 API 테스트")
public class ReserveControllerApiTest extends ApiTest {

	@Autowired
	private ShowingAdminService showingAdminService;

	@Test
	public void 공연_예약_API(){

		//given
		PerformanceRequest performanceRequest = new ShowingRequestFactory().create();
		UUID showingId = showingAdminService.register(performanceRequest);
		ReservationRequestFactory requestFactory = new ReservationRequestFactory();
		ReservationRequest request = requestFactory.create(showingId);

		//when
		ExtractableResponse<Response> result = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when()
			.post("/reserve")
			.then()
			.log().all().extract();

		//then
		ReservationResponse data = result.response().jsonPath()
			.getObject("data", ReservationResponse.class);

		AssertCluster.ReservationAssert(data,request,performanceRequest);

	}
}