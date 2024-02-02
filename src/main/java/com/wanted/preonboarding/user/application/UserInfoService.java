package com.wanted.preonboarding.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.user.domain.dto.PaymentSetting;
import com.wanted.preonboarding.user.domain.dto.SignUpInfo;
import com.wanted.preonboarding.user.domain.dto.UserAndPaymentInfo;
import com.wanted.preonboarding.user.domain.dto.UserInfo;
import com.wanted.preonboarding.user.domain.entity.Payment;
import com.wanted.preonboarding.user.domain.entity.PaymentCard;
import com.wanted.preonboarding.user.domain.entity.User;
import com.wanted.preonboarding.user.infrastructure.PaymentRepository;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoService(UserRepository userInfoRepository, PaymentRepository paymentRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userInfoRepository;
        this.paymentRepository = paymentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원 정보 저장 메서드
     * @param info
     */
    public void save(SignUpInfo info){
        info.setPassword(passwordEncoder.encode(info.getPassword()));
        userRepository.save(User.of(info));

    }

    /**
     * 회원 정보 조회 메서드
     * @param strUserUuid
     * @return
     */
    @Transactional(readOnly = true)
    public UserInfo getUserInfo(String strUserUuid) {
        UUID userUuid = UUID.fromString(strUserUuid);
        User user = userRepository.findById(userUuid)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
        return UserInfo.of(user);

    }

    /**
     * 유저 정보 및 결제 수단 정보 질의
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public UserAndPaymentInfo getUserAndPayments(String id){
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        PaymentCard paymentCard = user.getPaymentCards()
            .stream()
            .filter(card -> card.getId().equals(user.getDefaultPaymentCode()))
            .findAny()
            .orElseThrow(EntityNotFoundException::new);

        return UserAndPaymentInfo.of(user, paymentCard);
    }

    /**
     * 결제 정보 설정 메서드
     * @param paymentSetting
     * @param userUuid
     */
    public void setPaymentInfo(PaymentSetting paymentSetting, String userUuid){
        log.info("setPaymentInfo");
        PaymentCard payment = PaymentCard.of(paymentSetting, UUID.fromString(userUuid));
        paymentRepository.save(payment);
        User user = userRepository.getReferenceById(UUID.fromString(userUuid));
        user.updatedefaultPaymentCode(payment.getId());
        userRepository.save(user);
    }



}
