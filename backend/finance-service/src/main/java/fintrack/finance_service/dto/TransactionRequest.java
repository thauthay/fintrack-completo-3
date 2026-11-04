package fintrack.finance_service.dto;

import fintrack.finance_service.model.TransactionType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
		@NotBlank(message = "A descrição é obrigatória") @Size(max = 120) String description,
		@NotNull(message = "O valor é obrigatório")
		@Positive(message = "O valor deve ser maior que zero")
		@Digits(integer = 13, fraction = 2) BigDecimal amount,
		@NotNull(message = "O tipo é obrigatório") TransactionType type,
		@NotNull(message = "A categoria é obrigatória") Long categoryId,
		@NotNull(message = "A data é obrigatória") LocalDate date) {
}
