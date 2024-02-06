package com.wanted.preonboarding.account.infrastructure;

import com.wanted.preonboarding.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
}
