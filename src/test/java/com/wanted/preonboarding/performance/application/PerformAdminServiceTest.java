package com.wanted.preonboarding.performance.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceRequest;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("서비스: 관리자 공연 및 전시 등록 - PerformAdminService")
public class PerformAdminServiceTest extends ServiceTest {
	@Autowired
	private PerformAdminService performAdminService;

	@Test
	public void 관리자는_공연_전시_정보를_등록할_수_있다(){
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		UUID register = performAdminService.register(performanceRequest);
		assertThat(register).isNotNull();
	}

}