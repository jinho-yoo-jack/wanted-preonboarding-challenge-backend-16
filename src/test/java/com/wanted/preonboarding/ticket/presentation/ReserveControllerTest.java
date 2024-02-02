package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ReserveControllerTest {

    @Autowired
    final ReserveController res = null;

    @Autowired
    final QueryController q = null;

    @Test
    void reservationTest(){
        boolean result = res.reservation();
        assertThat(result);
    }

    @Test
    void performanceList(){
        ResponseEntity<ResponseHandler<List<PerformanceInfo>>> result = q.getAllPerformanceInfoList();
        assertThat(result);
    }

    @Test
    void reservationInfo(){
        ResponseEntity<ResponseHandler<ReserveInfo>> result = q.getRevervationInfo();
        assertThat(result);
    }

    @Test
    void ableReservationTest() {
        Boolean result = res.ableReservation();
        assertTrue(result);
    }

    @Test
    void cancelReservationTest() {
        Boolean result = res.cancelReservation();
        assertTrue(result);
    }

}