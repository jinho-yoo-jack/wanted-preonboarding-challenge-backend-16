package com.wanted.preonboarding.user.infrastructure;

import com.wanted.preonboarding.user.domain.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * UUID가 아닌 계정 ID로 엔티티 조회
     * @param id
     * @return
     */
    Optional<User> findById(String id);

    /**
     * 이름으로 엔티티 조회
     * @param name
     * @return
     */
    Optional<User> findByName(String name);

    /**
     * 전화번호로 레퍼런스 객체 조회
     * @param phoneNumber
     * @return
     */
    User getReferenceByPhoneNumber(String phoneNumber);
}
