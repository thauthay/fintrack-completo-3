package fintrack.notification_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import fintrack.notification_service.dto.NotificationRequest;
import fintrack.notification_service.dto.NotificationResponse;
import fintrack.notification_service.exception.ResourceNotFoundException;
import fintrack.notification_service.model.Notification;
import fintrack.notification_service.repository.NotificationRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

	@Mock
	private NotificationRepository notificationRepository;

	@InjectMocks
	private NotificationService service;

	@Test
	void createTrimsTextAndStartsUnread() {
		when(notificationRepository.save(any(Notification.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		NotificationResponse response = service
				.create(new NotificationRequest("  Gasto alto  ", "  Você gastou muito em Lazer.  "));

		assertEquals("Gasto alto", response.title());
		assertEquals("Você gastou muito em Lazer.", response.message());
		assertFalse(response.read());
	}

	@Test
	void markAsReadUpdatesNotification() {
		Notification notification = new Notification("Aviso", "Mensagem");
		when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

		NotificationResponse response = service.markAsRead(1L);

		assertTrue(response.read());
	}

	@Test
	void markAsReadFailsWhenNotificationDoesNotExist() {
		when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.markAsRead(99L));
	}
}
