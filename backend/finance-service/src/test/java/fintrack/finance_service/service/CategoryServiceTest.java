package fintrack.finance_service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fintrack.finance_service.dto.CategoryRequest;
import fintrack.finance_service.exception.ConflictException;
import fintrack.finance_service.model.Category;
import fintrack.finance_service.model.TransactionType;
import fintrack.finance_service.repository.CategoryRepository;
import fintrack.finance_service.repository.TransactionRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private CategoryService service;

	@Test
	void createRejectsDuplicateName() {
		when(categoryRepository.existsByNameIgnoreCase("Moradia")).thenReturn(true);

		assertThrows(ConflictException.class,
				() -> service.create(new CategoryRequest("Moradia", TransactionType.EXPENSE)));
		verify(categoryRepository, never()).save(any());
	}

	@Test
	void deleteRejectsCategoryInUse() {
		Category category = new Category("Moradia", TransactionType.EXPENSE);
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
		when(transactionRepository.existsByCategoryId(1L)).thenReturn(true);

		assertThrows(ConflictException.class, () -> service.delete(1L));
		verify(categoryRepository, never()).delete(any());
	}
}
