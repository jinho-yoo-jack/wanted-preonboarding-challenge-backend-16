package com.wanted.preonboarding.ticket.domain;

import com.wanted.preonboarding.core.code.NoticeType;
import com.wanted.preonboarding.ticket.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice")
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NoticeType type;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

}
