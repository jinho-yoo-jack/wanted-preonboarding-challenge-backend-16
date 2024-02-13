package com.wanted.preonboarding.layered.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Converter;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(nullable = true, name = "deleted_at")
    private LocalDateTime deletedAt;
}
