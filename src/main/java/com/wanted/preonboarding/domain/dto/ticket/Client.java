package com.wanted.preonboarding.domain.dto.ticket;

public class Client {
  private final String  name;
  private final String  phoneNum;
  private int           amount;

  public Client(String name, String phoneNum, int amount) {
    this.name = name;
    this.phoneNum = phoneNum;
    this.amount = amount;
  }

  public boolean pay(int ticketFee) {
    if (ticketFee > this.amount) return false;
    this.amount -= ticketFee;
    return true;
  }
}
