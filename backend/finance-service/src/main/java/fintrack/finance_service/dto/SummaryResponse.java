package fintrack.finance_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record SummaryResponse(
		BigDecimal balance,
		BigDecimal income,
		BigDecimal expenses,
		List<TransactionResponse> recentTransactions) {
}
