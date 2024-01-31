package com.wanted.preonboarding.performance.framwork.presentation;


import com.wanted.preonboarding.ApiTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.application.PerformAdminService;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("API: QueryController")
public class QueryControllerApiTest extends ApiTest {

	@Autowired
	private PerformAdminService performAdminService;

	@Test
	public void 공연_및_전시_정보_목록_조회_API(){
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformRequest performanceRequest = request.create();
		performAdminService.register(performanceRequest);

		//when
		ExtractableResponse<Response> result = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.queryParam("isReserve","true")
			.get("/query/all/performance")
			.then()
			.statusCode(HttpStatus.OK.value())
			.log().all().extract();

		//then
		List<PerformanceResponse> data = result.response().body().jsonPath().getList("data",
			PerformanceResponse.class);

		AssertCluster.performanceAssert(performanceRequest, data.get(0));
	}

	@Test
	public void 공연_및_전시_정보_상세_조회_API(){
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformRequest performanceRequest = request.create();
		UUID performId = performAdminService.register(performanceRequest);

		//when
		ExtractableResponse<Response> result = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.queryParam("id",performId)
			.get("/query/performance")
			.then()
			.statusCode(HttpStatus.OK.value())
			.log().all().extract();

		//then
		PerformanceResponse data = result.response().body().jsonPath().getObject("data",
			PerformanceResponse.class);

		AssertCluster.performanceAssert(performanceRequest, data);

	}

}