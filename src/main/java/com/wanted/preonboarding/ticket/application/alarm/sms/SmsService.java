package com.wanted.preonboarding.ticket.application.alarm.sms;

import java.util.List;

public interface SmsService {
    void sendMultipleSms(List<String> receiverPhoneNumbers, String msg );
}
