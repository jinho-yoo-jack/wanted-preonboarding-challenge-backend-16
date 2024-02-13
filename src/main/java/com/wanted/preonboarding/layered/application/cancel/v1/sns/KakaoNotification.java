package com.wanted.preonboarding.layered.application.cancel.v1.sns;

import com.wanted.preonboarding.layered.application.cancel.v1.Observer;
import com.wanted.preonboarding.layered.domain.dto.ReservationInfo;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public class KakaoNotification extends NotificationDecorator<ReservationInfo> {
    public KakaoNotification(Observer<ReservationInfo> observer) {
        this.t = observer;
    }

    @Override
    public boolean update(ReservationInfo message) throws MessagingException, UnsupportedEncodingException {
        sendMessage(message);
        return t.update(message);
    }

    @Override
    protected void sendMessage(ReservationInfo message) {
        System.out.println("[SEND to KakaoTalk]" + message);
    }
}
