package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link UserInfo}를 제어하기 위한 JPA 레포지토리 인터페이스입니다.
 * 유저 정보에 접근하기 위한 CRUD 연산을 제공합니다.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
  Optional<UserInfo> findUserInfoByUserNameAndPhoneNumber(String name, String phone);
}
