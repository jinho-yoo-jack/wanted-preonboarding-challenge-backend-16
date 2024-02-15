package com.wanted.preonboarding.ticket.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자의 정보(user_name과 phone_number)를 가지는 Entity입니다.
 * 여러 {@link Reservation}를 가질 수 있습니다.
 */
@Entity
@Table(name = "user_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int    id;

  @Column(name = "user_name", length = 255)
  private String userName;

  @Column(name = "phone_number", length = 255)
  private String phoneNumber;

  @OneToMany(mappedBy = "userInfo")
  private List<Reservation> reservations;
}
