package fintrack.notification_service.controller;

import fintrack.notification_service.dto.NotificationRequest;
import fintrack.notification_service.dto.NotificationResponse;
import fintrack.notification_service.dto.UnreadCountResponse;
import fintrack.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@GetMapping
	public List<NotificationResponse> list(@RequestParam(defaultValue = "false") boolean unread) {
		return notificationService.list(unread);
	}

	@GetMapping("/unread-count")
	public UnreadCountResponse unreadCount() {
		return notificationService.unreadCount();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public NotificationResponse create(@Valid @RequestBody NotificationRequest request) {
		return notificationService.create(request);
	}

	@PatchMapping("/{id}/read")
	public NotificationResponse markAsRead(@PathVariable Long id) {
		return notificationService.markAsRead(id);
	}
}
