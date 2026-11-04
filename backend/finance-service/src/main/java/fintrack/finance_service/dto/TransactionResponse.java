package fintrack.finance_service.dto;

import fintrack.finance_service.model.Transaction;
import fintrack.finance_service.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
		Long id,
		String description,
		BigDecimal amount,
		TransactionType type,
		Long categoryId,
		String category,
		LocalDate date) {

	public static TransactionResponse from(Transaction t) {
		return new TransactionResponse(
				t.getId(),
				t.getDescription(),
				t.getAmount(),
				t.getType(),
				t.getCategory().getId(),
				t.getCategory().getName(),
				t.getTransactionDate());
	}
}
