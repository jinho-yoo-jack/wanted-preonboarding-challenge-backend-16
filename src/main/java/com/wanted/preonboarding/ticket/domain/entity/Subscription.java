package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "subscription", uniqueConstraints = {
        @UniqueConstraint(name = "subscription_unique", columnNames = {"performance_id", "name", "phone_number"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID performanceId;

    @Embedded
    private UserInfo userInfo;
}
