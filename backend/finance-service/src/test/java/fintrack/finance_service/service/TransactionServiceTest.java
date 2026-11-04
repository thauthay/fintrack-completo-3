package fintrack.finance_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fintrack.finance_service.dto.TransactionRequest;
import fintrack.finance_service.dto.TransactionResponse;
import fintrack.finance_service.exception.BusinessRuleException;
import fintrack.finance_service.exception.ResourceNotFoundException;
import fintrack.finance_service.model.Category;
import fintrack.finance_service.model.Transaction;
import fintrack.finance_service.model.TransactionType;
import fintrack.finance_service.repository.CategoryRepository;
import fintrack.finance_service.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private TransactionService service;

	private TransactionRequest expenseRequest() {
		return new TransactionRequest("Mercado", new BigDecimal("50.00"),
				TransactionType.EXPENSE, 1L, LocalDate.of(2026, 10, 28));
	}

	@Test
	void createSavesWhenCategoryTypeMatches() {
		Category food = new Category("Alimentação", TransactionType.EXPENSE);
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(food));
		when(transactionRepository.save(any(Transaction.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		TransactionResponse response = service.create(expenseRequest());

		assertEquals("Mercado", response.description());
		assertEquals("Alimentação", response.category());
		assertEquals(TransactionType.EXPENSE, response.type());
	}

	@Test
	void createRejectsCategoryOfDifferentType() {
		Category salary = new Category("Salário", TransactionType.INCOME);
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(salary));

		assertThrows(BusinessRuleException.class, () -> service.create(expenseRequest()));
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void createFailsWhenCategoryDoesNotExist() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.create(expenseRequest()));
	}

	@Test
	void deleteFailsWhenTransactionDoesNotExist() {
		when(transactionRepository.existsById(99L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
		verify(transactionRepository, never()).deleteById(any());
	}
}
