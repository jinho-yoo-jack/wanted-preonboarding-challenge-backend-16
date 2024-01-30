package com.wanted.preonboarding.performance.presentation;


import com.wanted.preonboarding.ApiTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.application.ShowingAdminService;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("쿼리 controller API 테스트")
public class QueryControllerApiTest extends ApiTest {

	@Autowired
	private ShowingAdminService showingAdminService;

	@Test
	public void 공연_및_전시_정보_목록_조회_API(){
		//given
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		showingAdminService.register(performanceRequest);

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
		PerformanceRequest performanceRequest = request.create();
		showingAdminService.register(performanceRequest);

		//when
		ExtractableResponse<Response> result = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.queryParam("name",performanceRequest.name())
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