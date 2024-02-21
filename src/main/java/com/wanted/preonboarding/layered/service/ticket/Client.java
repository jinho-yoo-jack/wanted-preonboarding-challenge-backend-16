package com.wanted.preonboarding.layered.service.ticket;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Client {
  private final String  name;
  private final String  phoneNum;
  private Long          amount;
  private TierStrategy  tierStrategy;

  public Client(String name, String phoneNum, Long amount, TierStrategy tierStrategy) {
    this.name = name;
    this.phoneNum = phoneNum;
    this.amount = amount;
    this.tierStrategy = tierStrategy;
  }

  public boolean pay(Long ticketFee) {
    ticketFee = this.tierStrategy.getTierFee(ticketFee);
    if (ticketFee > this.amount) return false;
    this.amount -= ticketFee;
    return true;
  }
}
