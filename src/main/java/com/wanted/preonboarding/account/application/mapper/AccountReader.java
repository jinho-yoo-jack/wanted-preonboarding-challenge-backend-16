package com.wanted.preonboarding.account.application.mapper;

import com.wanted.preonboarding.account.domain.Account;
import com.wanted.preonboarding.account.infrastructure.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountReader {

    public static final String ACCOUNT_ERROR_MESSAGE_FORMAT = "[%d] 의 계좌가 존재하지 않습니다.";
    private final AccountRepository accountRepository;

    public Account findByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ACCOUNT_ERROR_MESSAGE_FORMAT, userId)));
    }
}
