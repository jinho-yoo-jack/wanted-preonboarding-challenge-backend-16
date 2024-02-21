package com.wanted.preonboarding.layered.service.ticket.discount.factory;

import com.wanted.preonboarding.domain.dto.enums.Tier;
import com.wanted.preonboarding.layered.service.ticket.TierStrategy;
import com.wanted.preonboarding.domain.entity.UserInfo;
import com.wanted.preonboarding.layered.repository.UserInfoRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TierDcFactory {
  final private UserInfoRepository userInfoRepository;

  public TierStrategy create(String userName, String phoneNum) {
    return switch (getTier(userName, phoneNum)) {
      case NORMAL -> (tierFee) -> BigDecimal.valueOf(tierFee).multiply(new BigDecimal(0.9)).longValue();
      case VIP -> (tierFee) -> BigDecimal.valueOf(tierFee).multiply(new BigDecimal(0.8)).longValue();
      default -> (tierFee) -> tierFee;
    };
  }
  private Tier getTier(String userName, String phoneNum) {
    UserInfo user = this.userInfoRepository
        .findUserInfoByUserNameAndPhoneNumber(userName, phoneNum)
        .orElse(null);
    if (user == null) return Tier.DEFAULT;
    return Tier.NORMAL;
  }
}
