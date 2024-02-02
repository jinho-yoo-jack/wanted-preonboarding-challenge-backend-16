package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import com.wanted.preonboarding.ticket.infrastructure.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmTicket {
    private final AlarmRepository alarmRepository;

    void sendAlarm(UUID uuid){
        List<Alarm> alarmInfoList = alarmRepository.findByPerformanceId(uuid);

        // 문자 보내기 coolsms API 사용
        String apiKey = "apiKey";
        String apiSecret = "apiSecret";

        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

        for (Alarm alarm : alarmInfoList) {

            Message message = new Message();

            String from = "01000000000";
            String to = alarm.getPhoneNumber().replaceAll("[^0-9]", "");
            String text = alarm.getName()+"님 "+alarm.getPerformanceName()+"("+alarm.getPerformanceId()+") 공연의 취소표가" +
                          "발생하였습니다.";

            message.setFrom(from);
            message.setTo(to);
            message.setText(text);

            try {
                messageService.send(message);
            } catch (NurigoMessageNotReceivedException exception) {
                System.out.println(exception.getFailedMessageList());
                System.out.println(exception.getMessage());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

        }
    }
}
