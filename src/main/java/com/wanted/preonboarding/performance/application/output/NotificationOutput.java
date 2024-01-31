package com.wanted.preonboarding.performance.application.output;

import java.util.List;
import java.util.UUID;

public interface NotificationOutput {

	void reservationCancelNotify(List<UUID> userIds, String message);
}
