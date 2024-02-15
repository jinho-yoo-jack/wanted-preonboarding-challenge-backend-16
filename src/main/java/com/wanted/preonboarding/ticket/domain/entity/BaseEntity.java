package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Setter;

@Setter
@MappedSuperclass
public class BaseEntity {
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createAt;
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
