package com.wanted.preonboarding.user.infrastructure;

import com.wanted.preonboarding.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
