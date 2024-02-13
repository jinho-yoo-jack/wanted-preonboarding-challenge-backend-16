package com.wanted.preonboarding.layered.application.cancel.v1;

import com.wanted.preonboarding.layered.application.cancel.v1.mail.EmailSender;
import com.wanted.preonboarding.layered.application.ticketing.v3.TicketOffice;
import com.wanted.preonboarding.layered.domain.dto.ReservationInfo;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class TicketCancelObserver implements Observer<ReservationInfo> {
    private final TicketOffice office;
    private final EmailSender emailSender;

    @Override
    public boolean update(ReservationInfo message) throws MessagingException, UnsupportedEncodingException {
        //TODO: message paring -> get subscribers in db -> new notify_message insert into notification_table
        office.getSubscribers(message.getPerformanceId());
        emailSender.sendEmail("customer@emailAddress.com",
            "취소된 티켓 정보 알려드립니다!",
            "<p>Hello,</p><p>This is a test email sent from Spring Boot.</p>"
            );
        return true;
    }
}
