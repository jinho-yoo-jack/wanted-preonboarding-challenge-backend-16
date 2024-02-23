package com.wanted.preonboarding.layered.service.ticket.factory;

import com.wanted.preonboarding.domain.dto.request.CreateReservationDto;
import com.wanted.preonboarding.layered.service.ticket.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientFactory {
  private final TierDcFactory tierDcFactoryFactory;

  public Client create(CreateReservationDto createReservationDto) {
    return Client.builder()
        .name(createReservationDto.userName())
        .phoneNum(createReservationDto.phoneNum())
        .amount(createReservationDto.amount())
        .tierStrategy(
            //  유저의 정보를 통해 할인 된 티켓 가격을 최종적으로 결제
            this.tierDcFactoryFactory.create(
                createReservationDto.userName(),
                createReservationDto.phoneNum())
        )
        .build();
  }
}
