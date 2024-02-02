package com.wanted.preonboarding.performance.framwork.infrastructure.notificationAdapter;

import com.wanted.preonboarding.performance.application.output.NotificationOutput;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class NotificationStub implements NotificationOutput {

	@Override
	public void reservationCancelNotify(List<UUID> userIds, String message) {
		throw new RuntimeException();
//		for (UUID each : userIds) {
//			System.out.println("ID : " + each + "님에게 알림이 발송되었습니다.");
//			System.out.println("MESSAGE : \"" + message + "\"");
//		}
	}
}
