package com.wanted.preonboarding.ticket.presentation;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class performanceControllerTests {
	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("GET /performance는 예매 가능/불가능 여부로 필터링한 공연 목록을 보여준다. 페이지 크기는 5")
	void t1() throws Exception {
		// When
		mvc.perform(get("/performance")
				.param("status", "enable"))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("예매 가능한 공연 목록 조회 성공"))
			.andExpect(jsonPath("$.data[0].name").value("영웅"))
			.andExpect(jsonPath("$.data.length()").value(5));

		mvc.perform(get("/performance")
				.param("status", "disable"))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("예매 불가능한 공연 목록 조회 성공"))
			.andExpect(jsonPath("$.data[0].name").value("레베카"))
			.andExpect(jsonPath("$.data.length()").value(5));
	}

	@Test
	@DisplayName("GET /performance는 필터링 기준을 주지 않으면 기본적으로 예매 가능한 공연 목록을 보여준다.")
	void t2() throws Exception {
		// When
		mvc.perform(get("/performance"))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("예매 가능한 공연 목록 조회 성공"))
			.andExpect(jsonPath("$.data[0].name").value("영웅"))
			.andExpect(jsonPath("$.data.length()").value(5));
	}


}
