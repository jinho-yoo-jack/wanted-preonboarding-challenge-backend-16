package com.wanted.preonboarding.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

@Entity
@Table(name = "waitlist")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "mail")
  private String  mail;

  @Column(name = "performance_id", columnDefinition = "BINARY(16)")
  private UUID    performanceId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserInfo user;
}
