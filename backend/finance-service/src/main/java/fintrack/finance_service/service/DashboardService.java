package fintrack.finance_service.service;

import fintrack.finance_service.dto.SummaryResponse;
import fintrack.finance_service.dto.TransactionResponse;
import fintrack.finance_service.model.TransactionType;
import fintrack.finance_service.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

	private final TransactionRepository transactionRepository;

	public DashboardService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Transactional(readOnly = true)
	public SummaryResponse summary() {
		BigDecimal income = transactionRepository.sumByType(TransactionType.INCOME);
		BigDecimal expenses = transactionRepository.sumByType(TransactionType.EXPENSE);
		List<TransactionResponse> recent = transactionRepository
				.findTop5ByOrderByTransactionDateDescIdDesc().stream()
				.map(TransactionResponse::from)
				.toList();
		return new SummaryResponse(income.subtract(expenses), income, expenses, recent);
	}
}
