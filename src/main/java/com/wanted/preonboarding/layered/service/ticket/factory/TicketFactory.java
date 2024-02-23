package com.wanted.preonboarding.layered.service.ticket.factory;

import com.wanted.preonboarding.domain.dto.request.CreateReservationDto;
import com.wanted.preonboarding.domain.entity.Performance;
import com.wanted.preonboarding.domain.exception.TicketException;
import com.wanted.preonboarding.layered.repository.PerformanceRepository;
import com.wanted.preonboarding.layered.service.ticket.Ticket;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class TicketFactory {
  private final DiscountPolicyFactory discountPolicyFactory;
  private final PerformanceRepository performanceRepository;

  //  요청 컨텍스트에서 Ticket 객체를 생성
  public Ticket create(CreateReservationDto req) throws TicketException {
    Performance performance = this.performanceRepository
        .findByNameAndRound(req.performanceName(), req.round()).orElseThrow(
            () -> new TicketException("공연 조회 실패", HttpStatus.NOT_FOUND));
    return Ticket.builder()
        .performanceId(performance.getId())
        .performanceName(performance.getName())
        .fixedPrice(performance.getPrice())
        .line(req.line())
        .round(req.round())
        .seat(req.seat())
        .appliedDiscountPolicies(
            req.discountPolicies().stream().map(
                //  Policy Factory에서 Policy 객체를 생성
                policyName -> this.discountPolicyFactory.getPolicy(
                    performance.getId(),
                    performance.getPrice(),
                    policyName
                    )
            ).collect(Collectors.toSet())
        ).build();
  }
}
