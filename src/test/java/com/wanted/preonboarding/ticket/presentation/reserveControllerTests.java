package com.wanted.preonboarding.ticket.presentation;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.event.EventCancleTicket;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@RecordApplicationEvents // 단일 테스트 실행하며 발생하는 애플리케이션 Event들을 record
public class reserveControllerTests {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private PerformanceRepository performanceRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	private UUID testPerformanceId1;
	private UUID testPerformanceId2;
	private Performance testPerformance;

	@Autowired
	// 스트림을 통해 이벤트 발생 클래스 넣어주고 카운트로 리턴
	// 이녀석을 사용하려면 @RecordApplicationEvents 필수
	ApplicationEvents events;

	@BeforeEach
	void setTestId() {
		testPerformanceId1 = performanceRepository.findByName("레베카").getId();
		testPerformanceId2 = performanceRepository.findByName("영웅").getId();
		testPerformance = performanceRepository.findByName("테스트용공연");
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

	@Test
	@DisplayName("GET /reserve는 예매자의 이름과 전화번호로 예매 내역을 조회할 수 있다")
	void t6() throws Exception {
		// When
		mvc.perform(get("/reserve")
				.param("name", "박철현")
				.param("phoneNumber", "010-1231-1231"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("예매 내역 조회 성공"))
			.andExpect(jsonPath("$.data[0].reservationName").value("박철현"))
			.andExpect(jsonPath("$.data[0].performanceName").value("영웅"))
			.andExpect(jsonPath("$.data[0].reservationPhoneNumber").value("010-1231-1231"));
	}

	@Test
	@DisplayName("GET /reserve는 예매자의 이름과 전화번호 모두 입력해야 예매 내역을 조회할 수 있다")
	void t7() throws Exception {
		// When
		mvc.perform(get("/reserve")
				.param("name", "박철현"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("F-")))
			.andExpect(jsonPath("$.msg").value("예약자 휴대폰 번호는 필수 항목입니다."));

		mvc.perform(get("/reserve")
				.param("phoneNumber", "010-1231-1231"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("F-")))
			.andExpect(jsonPath("$.msg").value("예약자 이름은 필수 항목입니다."));
	}

	@Test
	@DisplayName("POST /reserve는 마지막 자리를 예매하면 공연의 상태가 예약 불가능하게 변경된다.")
	void t8() throws Exception {
		String before = testPerformance.getIsReserve(); // equals("enable");
		// When
		mvc.perform(post("/reserve")
				.content("{"
					+ "\"performanceId\": \"" + testPerformance.getId() + "\","
					+ "\"reservationName\": \"유진호\","
					+ "\"reservationPhoneNumber\": \"010-1234-1234\","
					+ "\"reservationStatus\": \"reserve\","
					+ "\"amount\": \"100000\","
					+ "\"round\" : \"1\","
					+ "\"line\" : \"A\","
					+ "\"seat\" : 4"
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))

			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("예매 성공"))
			.andExpect(jsonPath("$.data.reservationName").value("유진호"));

		testPerformance = performanceRepository.findByName("테스트용공연");
		String after = testPerformance.getIsReserve(); // equals("disable");

		assertThat(before, is("enable"));
		assertThat(after, is("disable"));
	}

	@Test
	@DisplayName("POST /reserve는 마지막 자리를 예매하면 공연의 상태가 예약 불가능하게 변경된다.")
	void t9() throws Exception {
		String before = testPerformance.getIsReserve(); // equals("enable");
		// When
		mvc.perform(post("/reserve")
				.content("{"
					+ "\"performanceId\": \"" + testPerformance.getId() + "\","
					+ "\"reservationName\": \"유진호\","
					+ "\"reservationPhoneNumber\": \"010-1234-1234\","
					+ "\"reservationStatus\": \"reserve\","
					+ "\"amount\": \"100000\","
					+ "\"round\" : \"1\","
					+ "\"line\" : \"A\","
					+ "\"seat\" : 4"
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))

			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("예매 성공"))
			.andExpect(jsonPath("$.data.reservationName").value("유진호"));

		Reservation reservation = reservationRepository.findByNameAndPhoneNumber("유진호", "010-1234-1234").get(0);

		testPerformance = performanceRepository.findByName("테스트용공연");
		String after = testPerformance.getIsReserve(); // equals("disable");

		// 예매 불가 상태가 됨
		assertThat(before, is("enable"));
		assertThat(after, is("disable"));

		// 예매 취소 후에 이메일 발송 되는지 테스트
		// 예매 취소
		mvc.perform(delete("/reserve")
				.content("{"
					+ "\"id\": " + reservation.getId() + ","
					+ "\"name\": \"유진호\","
					+ "\"phoneNumber\": \"010-1234-1234\""
					+ "}")
				.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
			.andDo(print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.resultCode").value(startsWith("S-")))
			.andExpect(jsonPath("$.msg").value("유진호님의 예매 내역 삭제 성공"))
			.andExpect(jsonPath("$.data.name").value("유진호"));

		// 공연 예매 상태로 변경되었는지 확인
		testPerformance = performanceRepository.findByName("테스트용공연");
		after = testPerformance.getIsReserve(); // equals("enable");
		assertThat(after, is("enable"));

		// 알림 설정한 사람들에게 이메일 발송 테스트를 하기 위해 이벤트가 발행됐는지 테스트
		int count = (int) events.stream(EventCancleTicket.class).count();
		assertThat(count, is(1));
	}

}
