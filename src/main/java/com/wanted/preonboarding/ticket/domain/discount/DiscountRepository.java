package com.wanted.preonboarding.ticket.domain.discount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    @Query("select d from Discount d where d.performanceId = :performanceId and d.endDate > :requestTime")
    List<Discount> findDiscountsByPerformanceId(@Param("performanceId") UUID performanceId, @Param("requestTime") LocalDateTime requestTime);

}
