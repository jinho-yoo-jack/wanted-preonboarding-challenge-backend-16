package com.wanted.preonboarding.performance.framwork.infrastructure.eventAdapter;

import com.wanted.preonboarding.performance.application.PerformService;
import com.wanted.preonboarding.reservation.domain.event.ReserveEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformLister {

	private final PerformService performService;

	public boolean isAvailable(ReserveEvent reserveEvent) {
		UUID performId = reserveEvent.getItemId();
		return performService.isAvailable(performId);
	}

}
