package com.wanted.preonboarding.ticket.application.event;

import com.wanted.preonboarding.ticket.application.dto.AwaitInfo;
import com.wanted.preonboarding.ticket.application.dto.MessageInfo;
import com.wanted.preonboarding.ticket.application.event.dto.AlarmInfo;
import com.wanted.preonboarding.ticket.application.interfaces.AlarmSender;
import com.wanted.preonboarding.ticket.application.mapper.PerformanceReader;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.AwaitRepository;
import com.wanted.preonboarding.user.User;
import com.wanted.preonboarding.user.application.mapper.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AlarmEventHandler {

    private final PerformanceReader performanceReader;
    private final AwaitRepository awaitRepository;
    private final AlarmSender alarmSender;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void sendMessage(AlarmEvent<AlarmInfo> alarmEvent) {
        Performance performance = performanceReader.findById(alarmEvent.getData().performanceId());
        List<AwaitInfo> awaitInfos = awaitRepository.findByPerformanceId(performance.getId());

        for (AwaitInfo awaitInfo: awaitInfos) {
            MessageInfo messageInfo = createMessage(alarmEvent, awaitInfo, performance);
            alarmSender.sendMessage(messageInfo);
        }

    }

    private MessageInfo createMessage(AlarmEvent<AlarmInfo> alarmEvent, AwaitInfo awaitInfo, Performance performance) {
        return MessageInfo.builder()
                .reservationName(awaitInfo.name())
                .performanceName(performance.getName())
                .startDateTime(performance.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME))
                .round(alarmEvent.getData().round())
                .line(alarmEvent.getData().line())
                .gate(alarmEvent.getData().gate())
                .seat(alarmEvent.getData().seat())
                .build();
    }
}
