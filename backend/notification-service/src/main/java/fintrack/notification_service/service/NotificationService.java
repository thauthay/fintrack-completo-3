package fintrack.notification_service.service;

import fintrack.notification_service.dto.NotificationRequest;
import fintrack.notification_service.dto.NotificationResponse;
import fintrack.notification_service.dto.UnreadCountResponse;
import fintrack.notification_service.exception.ResourceNotFoundException;
import fintrack.notification_service.model.Notification;
import fintrack.notification_service.repository.NotificationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;

	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Transactional(readOnly = true)
	public List<NotificationResponse> list(boolean unreadOnly) {
		List<Notification> notifications = unreadOnly
				? notificationRepository.findByReadFalseOrderByCreatedAtDescIdDesc()
				: notificationRepository.findAllByOrderByCreatedAtDescIdDesc();
		return notifications.stream().map(NotificationResponse::from).toList();
	}

	@Transactional
	public NotificationResponse create(NotificationRequest request) {
		Notification notification = new Notification(request.title().trim(), request.message().trim());
		return NotificationResponse.from(notificationRepository.save(notification));
	}

	@Transactional
	public NotificationResponse markAsRead(Long id) {
		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notificação " + id + " não encontrada."));
		notification.markAsRead();
		return NotificationResponse.from(notification);
	}

	@Transactional(readOnly = true)
	public UnreadCountResponse unreadCount() {
		return new UnreadCountResponse(notificationRepository.countByReadFalse());
	}
}
