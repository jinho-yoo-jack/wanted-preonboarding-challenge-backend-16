package com.wanted.preonboarding.ticket.infrastructure.reader;

import com.wanted.preonboarding.core.code.ActiveType;
import com.wanted.preonboarding.ticket.domain.entity.DiscountInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.DiscountInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountInfoReader {

    private final DiscountInfoRepository discountInfoRepository;

    @Transactional(readOnly = true)
    public List<DiscountInfo> getActiveDiscountInfos() {
        return discountInfoRepository.findAllByIsActive(ActiveType.OPEN);
    }
}
