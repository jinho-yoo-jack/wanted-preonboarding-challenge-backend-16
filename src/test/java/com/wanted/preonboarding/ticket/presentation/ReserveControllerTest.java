package com.wanted.preonboarding.ticket.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ReserveControllerTest {

    @Autowired
    final ReserveController res = null;

    @Test
    void reservationTest(){
        boolean result = res.reservation();
        assertThat(result);
    }

}