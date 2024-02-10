package com.wanted.preonboarding.ticketing.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Time {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("생성 시간")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Comment("업데이트 시간")
    private LocalDateTime updatedAt;
}
