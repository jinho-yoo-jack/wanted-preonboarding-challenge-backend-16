package com.wanted.preonboarding.domain.entity;

import com.wanted.preonboarding.domain.dto.enums.PerformanceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Performance} 클래스는 티켓 시스템의 "공연 정보" Entity입니다.
 * JPA를 통해 'performance' 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "performance")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Performance extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private int round;

  @Column(nullable = false, columnDefinition = "integer")
  @Enumerated(EnumType.ORDINAL)
  private PerformanceType type;

  @Column(nullable = false)
  private LocalDateTime startDate;

  @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
  private String isReserve;

  @OneToMany(mappedBy = "performance")
  private List<SeatInfo> seats;
}
