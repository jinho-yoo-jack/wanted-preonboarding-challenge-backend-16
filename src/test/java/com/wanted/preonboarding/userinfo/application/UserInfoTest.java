package com.wanted.preonboarding.userinfo.application;

import com.wanted.preonboarding.user.domain.entity.PaymentCard;
import com.wanted.preonboarding.user.domain.entity.User;
import com.wanted.preonboarding.user.infrastructure.PaymentRepository;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInfoTest {

    @Autowired
    private UserRepository userInfoRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Test
    public void userSaveTest(){

        User user = User.builder()
            .name("testName")
            .id("test1111")
            .password("1111")
            .email("test@test.com")
            .phoneNumber("01011112222")
            .birthday(new Date(19970601L))
            .createdAt(LocalDateTime.now())
            .build();
        userInfoRepository.save(user);
    }

    @Test
    public void findByIdTest(){
        User user = userInfoRepository.findById("avangard111").orElseThrow(
            () -> new EntityNotFoundException("존재하지 안흔ㄴ 회원")
        );
        System.out.println("user name: " + user.getName());
    }

    @Test
    public void paymentCardSaveTest(){
        PaymentCard payment = PaymentCard.builder()
                            .id(1L)
                            .userInfo(
                                User.builder()
                                    .userUuid(UUID.fromString("0bfde5b5-1850-41c9-b6d7-87242c003390"))
                                    .build())
                            .balanceAmount(100000L)
                            .cardNum("5022212343344456")
                            .expiredDate("07/25")
                            .cvc("111")
                            .build();

        paymentRepository.save(payment);
    }

    @Test
    @Transactional
    public void selectPaymentCardTest(){
        User user = userInfoRepository.findById(UUID.fromString("8282d75a-bc5d-11ee-8f0f-0242ac150002")).orElseThrow(() -> new EntityNotFoundException("엔티티 없음"));
        System.out.println("name: " + user.getName());
        System.out.println("paymentCards: " + user.getPaymentCards());
    }

}
