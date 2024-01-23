package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;

    /*
        예매 결과 응답
     */
    @PostMapping("")
    public RsData reservation(@RequestBody ReserveInfo reserveInfo) {
        RsData reserveResult = ticketSeller.reserve(reserveInfo);
        if(reserveResult.isFail())
            return RsData.of(reserveResult.getResultCode(), reserveResult.getMsg());
        // 성공할때만 데이터 같이 넘기기
        return reserveResult;
    }
}
