package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender javaMailSender;

    public void sendPerformanceInfo(String email, PerformanceSeatInfo performanceSeatInfo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject(performanceSeatInfo.getPerformance().getName() + " 예약 알림");
        mailMessage.setText(writeBody(performanceSeatInfo));

        javaMailSender.send(mailMessage);
    }

    private String writeBody(PerformanceSeatInfo performanceSeatInfo) {
        Performance performance = performanceSeatInfo.getPerformance();

        return "안녕하세요, 공연 예약 알림입니다.\n\n" +
                "공연 ID: " + performance.getId() + "\n" +
                "공연명: " + performance.getName() + "\n" +
                "회차: " + performance.getRound() + "\n" +
                "시작 일시: " + performance.getStartDate() + "\n" +
                "예매 가능한 좌석 정보 : \n" +
                "- 좌석 ID : " + performanceSeatInfo.getId() + "\n" +
                "- 입장 게이트 : " + performanceSeatInfo.getGate() + "\n" +
                performanceSeatInfo.getSeat() + "- 좌석 위치 : 열 " + performanceSeatInfo.getLine() + "행" +
                "\n\n 감사합니다.";
    }
}
