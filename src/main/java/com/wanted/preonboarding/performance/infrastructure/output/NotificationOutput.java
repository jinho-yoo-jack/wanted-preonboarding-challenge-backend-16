package com.wanted.preonboarding.performance.infrastructure.output;

import java.util.List;
import java.util.UUID;

public interface NotificationOutput {

	void reservationCancelNotify(List<UUID> userIds, String message);
}
