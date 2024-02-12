package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.core.code.ActiveType;
import com.wanted.preonboarding.ticket.domain.entity.DiscountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountInfoRepository extends JpaRepository<DiscountInfo, Integer> {
    List<DiscountInfo> findAllByIsActive(ActiveType open);
}
