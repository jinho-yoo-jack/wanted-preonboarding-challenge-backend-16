package com.wanted.preonboarding.ticket.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.PerformanceRequestFactory;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("관리자 공연 및 전시 등록 서비스 테스트")
public class PerformanceAdminServiceTest {
	@Autowired
	private PerformanceAdminService performanceAdminService;

	@Test
	public void 관리자는_공연_전시_정보를_등록할_수_있다(){
		PerformanceRequestFactory request = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = request.create();
		UUID register = performanceAdminService.register(performanceRequest);
		assertThat(register).isNotNull();
	}

}