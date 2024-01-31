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
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    @Autowired
    public UserInfoService(UserRepository userInfoRepository, PaymentRepository paymentRepository, PasswordEncoder passwordEncoder, ObjectMapper objectMapper){
        this.userRepository = userInfoRepository;
        this.paymentRepository = paymentRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    public void save(SignUpInfo info){
        info.setPassword(passwordEncoder.encode(info.getPassword()));
        userRepository.save(User.of(info));

    }

    public UserInfo getUserInfo(String strUserUuid) {
        UUID userUuid = UUID.fromString(strUserUuid);
        User user = userRepository.findById(userUuid)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
        return UserInfo.of(user);

    }

    public UserAndPaymentInfo getUserAndPayments(String userUuid){
        User user = userRepository.findById(UUID.fromString(userUuid)).orElseThrow(EntityNotFoundException::new);
        String paymentCode = user.getDefaultPaymentCode();

        if(paymentCode.equals("1")){
            log.info("paymentcode is 1");
            return UserAndPaymentInfo.of(user, user.getPaymentCards());
        }
        log.info("paymentcode is not 1");
        return UserAndPaymentInfo.of(user, user.getPaymentPoints());
    }

    public void setPaymentInfo(PaymentSetting paymentSetting, String userUuid){
        log.info("setPaymentInfo");
        PaymentCard payment = PaymentCard.of(paymentSetting, UUID.fromString(userUuid));
        paymentRepository.save(payment);
    }



}
