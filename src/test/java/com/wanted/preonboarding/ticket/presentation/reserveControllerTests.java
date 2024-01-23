package com.wanted.preonboarding.ticket.presentation;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
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
public class reserveControllerTests {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private PerformanceRepository performanceRepository;

	private UUID testPerformanceId1;
	private UUID testPerformanceId2;

	@BeforeEach
	void setTestId() {
		testPerformanceId1 = performanceRepository.findByName("레베카").getId();
		testPerformanceId2 = performanceRepository.findByName("영웅").getId();
	}

	@Test
	@DisplayName("POST /reserve는 특정 공연 예약이 불가능하면 예약 실패한다.")
	void t1() throws Exception {
		// When
		mvc.perform(post("/reserve")
				.content("{"
					+ "\"performanceId\": \"" + testPerformanceId1 + "\","
					+ "\"reservationName\": \"유진호\","
					+ "\"reservationPhoneNumber\": \"010-1234-1234\","
					+ "\"reservationStatus\": \"reserve\","
					+ "\"amount\": \"200000\","
					+ "\"round\" : \"1\","
					+ "\"line\" : \"A\","
					+ "\"seat\" : 1"
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))

			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("F-")))
			.andExpect(jsonPath("$.msg").value("해당 공연은 예약 가능한 상태가 아닙니다."));
	}

	@Test
	@DisplayName("POST /reserve는 특정 공연 예약이 가능하지만 없는 자리일 경우 불가능")
	void t2() throws Exception {
		// When
		mvc.perform(post("/reserve")
				.content("{"
					+ "\"performanceId\": \"" + testPerformanceId2 + "\","
					+ "\"reservationName\": \"유진호\","
					+ "\"reservationPhoneNumber\": \"010-1234-1234\","
					+ "\"reservationStatus\": \"reserve\","
					+ "\"amount\": \"200000\","
					+ "\"round\" : \"1\","
					+ "\"line\" : \"F\","
					+ "\"seat\" : 18"
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))

			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("F-")))
			.andExpect(jsonPath("$.msg").value("해당 좌석은 존재하지 않습니다"));
	}

	@Test
	@DisplayName("POST /reserve는 특정 공연 예약이 가능하지만 다른 사람이 예약 중인 경우는 예약 불가능")
	void t3() throws Exception {
		// When
		mvc.perform(post("/reserve")
				.content("{"
					+ "\"performanceId\": \"" + testPerformanceId2 + "\","
					+ "\"reservationName\": \"유진호\","
					+ "\"reservationPhoneNumber\": \"010-1234-1234\","
					+ "\"reservationStatus\": \"reserve\","
					+ "\"amount\": \"200000\","
					+ "\"round\" : \"1\","
					+ "\"line\" : \"A\","
					+ "\"seat\" : 18"
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))

			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("F-")))
			.andExpect(jsonPath("$.msg").value("해당 좌석은 이미 다른 사용자가 예매중입니다."));
	}


	@Test
	@DisplayName("POST /reserve는 특정 공연 예약이 가능하고, 사용자가 돈이 충분하면 예매 가능")
	void t4() throws Exception {
		// When
		mvc.perform(post("/reserve")
				.content("{"
					+ "\"performanceId\": \"" + testPerformanceId2 + "\","
					+ "\"reservationName\": \"유진호\","
					+ "\"reservationPhoneNumber\": \"010-1234-1234\","
					+ "\"reservationStatus\": \"reserve\","
					+ "\"amount\": \"200000\","
					+ "\"round\" : \"1\","
					+ "\"line\" : \"A\","
					+ "\"seat\" : 1"
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))

			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("예매 성공"))
			.andExpect(jsonPath("$.data.reservationName").value("유진호"));
	}

	@Test
	@DisplayName("POST /reserve는 특정 공연 예약이 가능하고, 사용자가 돈이 부족하면 예매 불가")
	void t5() throws Exception {
		// When
		mvc.perform(post("/reserve")
				.content("{"
					+ "\"performanceId\": \"" + testPerformanceId2 + "\","
					+ "\"reservationName\": \"유진호\","
					+ "\"reservationPhoneNumber\": \"010-1234-1234\","
					+ "\"reservationStatus\": \"reserve\","
					+ "\"amount\": \"60000\","
					+ "\"round\" : \"1\","
					+ "\"line\" : \"A\","
					+ "\"seat\" : 1"
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))

			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("F-")))
			.andExpect(jsonPath("$.msg").value("해당 공연을 예매하기 위한 예산이 부족합니다."));
	}

}
