package fintrack.notification_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NotificationRequest(
		@NotBlank(message = "O título é obrigatório") @Size(max = 120) String title,
		@NotBlank(message = "A mensagem é obrigatória") @Size(max = 500) String message) {
}
