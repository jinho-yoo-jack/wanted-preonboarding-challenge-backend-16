package com.wanted.preonboarding.ticket.application.alarm.sms;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsoleSmsServiceImpl implements SmsService{

    @Override
    public void sendMultipleSms(List<String> receiverPhoneNumbers, String msg){
        for (String receiverPhoneNumber : receiverPhoneNumbers) {
            System.out.println("-----------------------------");
            System.out.println(receiverPhoneNumber + "에게 발송.");
            System.out.println(msg);
        }
    }
}
