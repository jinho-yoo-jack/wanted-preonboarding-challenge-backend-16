package com.wanted.preonboarding.account.infrastructure;

import com.wanted.preonboarding.account.domain.Account;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findByUserId(Long userId);
}
