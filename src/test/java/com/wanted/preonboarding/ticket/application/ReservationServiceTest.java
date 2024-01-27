package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.account.domain.Account;
import com.wanted.preonboarding.account.domain.exception.NotEnoughMoneyException;
import com.wanted.preonboarding.account.domain.vo.Money;
import com.wanted.preonboarding.account.infrastructure.AccountRepository;
import com.wanted.preonboarding.account.support.AccountFactory;
import com.wanted.preonboarding.ticket.application.dto.request.CreateReserveServiceRequest;
import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.application.exception.AlreadyReservedStateException;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.user.User;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import com.wanted.preonboarding.user.support.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ReservationService reservationService;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.create();
        user = userRepository.save(user);
    }

    @DisplayName("공연을 예약 할 수 있다. 공연 금액은 10만으로 주어짐")
    @Test
    void reserve() {
        // given
        CreateReserveServiceRequest request = CreateReserveServiceRequest.builder()
                .userId(user.getId())
                .performanceSeatInfoId(1L)
                .build();

        Account account = AccountFactory.create(user.getId(), BigDecimal.valueOf(100000));
        accountRepository.save(account);

        // when
        ReserveResponse reserve = reservationService.reserve(request);
        Optional<Account> findAccount = accountRepository.findByUserId(user.getId());

        // then
        assertThat(findAccount.get().getMoney().getAmount().compareTo(BigDecimal.valueOf(1000))).isEqualTo(0);
        assertThat(reserve).isNotNull();
    }

    @DisplayName("예약이 불가능 한 공연이라면 예약 할 수 없다.")
    @Test
    void reserveWithPerformanceIsDisabled() {
        // given
        CreateReserveServiceRequest request = CreateReserveServiceRequest.builder()
                .userId(user.getId())
                .performanceSeatInfoId(5L)
                .build();

        Account account = AccountFactory.create(user.getId(), BigDecimal.valueOf(100000));
        accountRepository.save(account);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(AlreadyReservedStateException.class);
    }

    @DisplayName("좌석이 예약이 불가능 한 좌석이라면 예약 할 수 없다.")
    @Test
    void reserveWithPerformanceSeatIsDisabled() {
        // given
        CreateReserveServiceRequest request = CreateReserveServiceRequest.builder()
                .userId(user.getId())
                .performanceSeatInfoId(3L)
                .build();

        Account account = AccountFactory.create(user.getId(), BigDecimal.valueOf(100000));
        accountRepository.save(account);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(AlreadyReservedStateException.class);
    }

    @DisplayName("지불할 금액이 부족하다면 예약을 할 수 없다.")
    @Test
    void reserveWithAccountNotEnoughMoney() {
        // given
        CreateReserveServiceRequest request = CreateReserveServiceRequest.builder()
                .userId(user.getId())
                .performanceSeatInfoId(1L)
                .build();

        Account account = AccountFactory.create(user.getId(), BigDecimal.valueOf(10000));
        accountRepository.save(account);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(NotEnoughMoneyException.class)
                .hasMessage(String.format(Money.NOT_ENOUGH_MESSAGE_FORMAT, 10000.0));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }
}