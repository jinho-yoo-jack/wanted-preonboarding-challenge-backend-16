package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.coyote.BadRequestException;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="performance", uniqueConstraints = {
        @UniqueConstraint(name = "performance_unique", columnNames = {"id", "round"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private Integer price;

    private Integer round;

    private PerformanceType type;

    private LocalDateTime startDate;

    @Column(columnDefinition = "varchar default 'disable'")
    @Convert(converter = EnableDisableConverter.class)
    private Boolean isReserve;
    
    @OneToMany(mappedBy = "performance")
    private List<PerformanceSeatInfo> seatInfos;
}
