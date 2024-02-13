package com.wanted.preonboarding.layered.application.ticketing.v3;

import com.wanted.preonboarding.layered.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.layered.domain.dto.request.RequestReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketSellerV3 {
    private final TicketOffice ticketOffice;
    private final TicketFactory ticketFactory;

    public List<PerformanceInfo> fetchAllPerformanceInfoList() {
        return ticketOffice.getAllPerformanceInfoList();
    }

    public TicketV3 sellTo(RequestReservation requestReservationInfo) throws Exception {
        return ticketFactory.ticketing(requestReservationInfo)
            .calculateTotalFee();
    }
}
