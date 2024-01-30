package com.wanted.preonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 원티드 프리 온보딩 사전과제 어플리케이션입니다.
 * <p>공연의 예약과 조회 정보를 제공하며 특정 공연의 예약 취소가 발생하는 경우,
 * 기존 대기열의 고객에게 알리는 기능을 가집니다.</p>.
 */
@SpringBootApplication
public class BackendPreonboardingApplication {
  public static void main(String[] args) {
    SpringApplication.run(BackendPreonboardingApplication.class, args);
  }
}
