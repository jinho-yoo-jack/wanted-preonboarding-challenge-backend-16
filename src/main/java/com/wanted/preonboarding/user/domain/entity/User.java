package com.wanted.preonboarding.user.domain.entity;

import com.wanted.preonboarding.user.domain.dto.SignUpInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 *    유저 정보 엔티티
 *    private UUID userUuid;                //유저 UUID
 *    private String name;                  // 유저 이름
 *    private String id;                    // 유저 ID
 *    private String email;                 // 유저 이메일
 *    private String password;              // 유저 비밀번호
 *    private String phoneNumber;           // 유저 휴대전화 번호
 *    private Date birthday;                // 유저 생일
 *    private LocalDateTime createdAt;      // 유저 가입 일시
 *    private int defaultPaymentCode;       // 기본 결제 수단
 */

@Table(
    name = "user_info",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "user_info_unique",
            columnNames = {
                "id",
                "email",
                "phone_number"
            }
        )
    }
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(generator = "uuid") // 생성한 uuid Generator을 사용
    @GenericGenerator(name = "uuid", strategy = "uuid2")    // UUIDGenerator를 이용하는 Generator 생성, 이름은 uuid
    private UUID userUuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private Date birthday;
    @Column(nullable = true)
    private LocalDateTime createdAt;
    private Long defaultPaymentCode;
    @OneToMany(mappedBy = "userInfo")
    private List<PaymentCard> paymentCards;
    @OneToMany(mappedBy = "userInfo")
    private List<PaymentPoint> paymentPoints;

    public static User of(SignUpInfo info){
        return User.builder()
            .name(info.getName())
            .id(info.getId())
            .password(info.getPassword())
            .email(info.getEmail())
            .phoneNumber(info.getPhoneNumber())
            .birthday(info.getBirthday())
            .build();

    }
    public void updatedefaultPaymentCode(Long paymentCardId){
        this.defaultPaymentCode = paymentCardId;
    }
}
