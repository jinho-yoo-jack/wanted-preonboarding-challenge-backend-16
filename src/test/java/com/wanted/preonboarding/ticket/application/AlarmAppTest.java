package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.ResponseAlarmDto;
import com.wanted.preonboarding.ticket.domain.dto.SubscriberPerformanceRequestDto;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.ReservationAlarm;
import com.wanted.preonboarding.ticket.infrastructure.repository.AlarmRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.SubscribeReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class AlarmAppTest {

    @Mock
    private PerformanceRepository performanceRepository;
    @Mock
    private SubscribeReservationRepository subscribeReservationRepository;
    @Mock
    private AlarmRepository alarmRepository;
    @InjectMocks
    private AlarmApp alarmApp;
    private SubscriberPerformanceRequestDto requestDto;
    private final Performance performance = mock(Performance.class);
    private UUID testUUID;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testUUID = UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002");
        requestDto = new SubscriberPerformanceRequestDto(testUUID,"testMember");
    }
    @Test
    @DisplayName("subscribeReservation")
    void subscribeReservation() throws Exception {
        //given
        when(performanceRepository.findById(any(UUID.class))).thenReturn(Optional.of(performance));
        //when
        alarmApp.subscribeReservation(requestDto);
        //then
        verify(subscribeReservationRepository, times(1)).save(any(ReservationAlarm.class));
    }

    @Test
    @DisplayName("sendAlarm")
    void sendAlarm() throws Exception {
        //given
        List<ReservationAlarm> subscribers = new ArrayList<>();
        subscribers.add(new ReservationAlarm(performance,"testMember1"));
        subscribers.add(new ReservationAlarm(performance,"testMember2"));
        subscribers.add(new ReservationAlarm(performance,"testMember3"));

        //when
        when(subscribeReservationRepository.findByPerformanceId(testUUID)).thenReturn(subscribers);
        when(performanceRepository.findById(requestDto.getPerformanceId())).thenReturn(Optional.ofNullable(performance));

        alarmApp.sendAlarm(testUUID);
        //then
        verify(alarmRepository,times(subscribers.size())).save(any(Alarm.class));
    }

    @Test
    @DisplayName("getAlarm")
    void getAlarm() throws Exception {
        //given
        String memberName = "testMember";
        Alarm alarm = Alarm.of(memberName,performance);
        when(alarmRepository.findByMemberName(memberName)).thenReturn(Optional.of(alarm));

        //when
        ResponseAlarmDto responseAlarmDto = alarmApp.getAlarm(memberName);

        //then
        assertEquals(alarm.toResponseDto().getReceiver(), responseAlarmDto.getReceiver());
    }

    
}