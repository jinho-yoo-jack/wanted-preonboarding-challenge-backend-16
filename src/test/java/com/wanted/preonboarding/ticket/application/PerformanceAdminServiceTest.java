package com.wanted.preonboarding.ticket.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.RequestFactory;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PerformanceAdminServiceTest {
	@Autowired
	private PerformanceAdminService performanceAdminService;

	@Test
	public void 관리자는_공연_전시_정보를_등록할_수_있다(){
		PerformanceRequest performanceRequest = RequestFactory.getPerformanceRequest();
		UUID register = performanceAdminService.register(performanceRequest);
		assertThat(register).isNotNull();
	}

}