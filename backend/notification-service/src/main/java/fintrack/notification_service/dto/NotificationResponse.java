package fintrack.notification_service.dto;

import fintrack.notification_service.model.Notification;
import java.time.Instant;

public record NotificationResponse(Long id, String title, String message, boolean read, Instant createdAt) {

	public static NotificationResponse from(Notification notification) {
		return new NotificationResponse(notification.getId(), notification.getTitle(),
				notification.getMessage(), notification.isRead(), notification.getCreatedAt());
	}
}
