package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ApiTest;
import com.wanted.preonboarding.ticket.AssertCluster;
import com.wanted.preonboarding.ticket.ReservationRequestFactory;
import com.wanted.preonboarding.ticket.ShowingRequestFactory;
import com.wanted.preonboarding.ticket.application.ShowingAdminService;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReservationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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