package fintrack.finance_service.dto;

import fintrack.finance_service.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
		@NotBlank(message = "O nome é obrigatório") @Size(max = 60) String name,
		@NotNull(message = "O tipo é obrigatório") TransactionType type) {
}
