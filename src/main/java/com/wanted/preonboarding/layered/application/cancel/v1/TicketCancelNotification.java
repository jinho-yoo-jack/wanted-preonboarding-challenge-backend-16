package com.wanted.preonboarding.layered.application.cancel.v1;

import com.wanted.preonboarding.layered.domain.dto.ReservationInfo;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TicketCancelNotification implements Observable<ReservationInfo> {
    private final Set<Observer<ReservationInfo>> subscribers;
    private ReservationInfo info;

    public void getMessage(ReservationInfo info){
        this.info = info;
    }

    @Override
    public boolean register(Observer<ReservationInfo> o) {
        // TODO: Insert into Table subscribe info
        return subscribers.add(o);
    }

    @Override
    public boolean remove(Observer o) {
        // TODO: Delete into Table subscribe info
        return subscribers.remove(o);
    }

    @Override
    public boolean sendMessage() {
        // TODO: Called from Ticket Cancel Service
        return subscribers.stream().filter(observer -> {
            try {
                return observer.update(info);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).count() == subscribers.size();
    }

}
