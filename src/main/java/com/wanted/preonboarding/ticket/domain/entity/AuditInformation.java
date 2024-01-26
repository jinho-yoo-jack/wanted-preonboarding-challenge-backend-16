package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditInformation {
    @CreatedDate
    @Comment("등록시간")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Comment("수정시간")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
