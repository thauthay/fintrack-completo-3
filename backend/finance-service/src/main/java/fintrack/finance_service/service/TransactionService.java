package fintrack.finance_service.service;

import fintrack.finance_service.dto.TransactionRequest;
import fintrack.finance_service.dto.TransactionResponse;
import fintrack.finance_service.exception.BusinessRuleException;
import fintrack.finance_service.exception.ResourceNotFoundException;
import fintrack.finance_service.model.Category;
import fintrack.finance_service.model.Transaction;
import fintrack.finance_service.model.TransactionType;
import fintrack.finance_service.repository.CategoryRepository;
import fintrack.finance_service.repository.TransactionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;
	private final CategoryRepository categoryRepository;

	public TransactionService(TransactionRepository transactionRepository,
			CategoryRepository categoryRepository) {
		this.transactionRepository = transactionRepository;
		this.categoryRepository = categoryRepository;
	}

	@Transactional(readOnly = true)
	public List<TransactionResponse> list(TransactionType type, Long categoryId, String search) {
		String term = search == null ? "" : search.trim();
		return transactionRepository.search(type, categoryId, term).stream()
				.map(TransactionResponse::from)
				.toList();
	}

	@Transactional
	public TransactionResponse create(TransactionRequest request) {
		Category category = loadCategoryFor(request);
		Transaction transaction = new Transaction(
				request.description().trim(),
				request.amount(),
				request.type(),
				category,
				request.date());
		return TransactionResponse.from(transactionRepository.save(transaction));
	}

	@Transactional
	public TransactionResponse update(Long id, TransactionRequest request) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transação " + id + " não encontrada."));
		Category category = loadCategoryFor(request);

		transaction.setDescription(request.description().trim());
		transaction.setAmount(request.amount());
		transaction.setType(request.type());
		transaction.setCategory(category);
		transaction.setTransactionDate(request.date());
		return TransactionResponse.from(transaction);
	}

	@Transactional
	public void delete(Long id) {
		if (!transactionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Transação " + id + " não encontrada.");
		}
		transactionRepository.deleteById(id);
	}

	/** Regra de negócio: o tipo da transação precisa bater com o tipo da categoria. */
	private Category loadCategoryFor(TransactionRequest request) {
		Category category = categoryRepository.findById(request.categoryId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Categoria " + request.categoryId() + " não encontrada."));
		if (category.getType() != request.type()) {
			throw new BusinessRuleException("A categoria \"" + category.getName()
					+ "\" é de " + category.getType().getValue()
					+ " e não pode receber uma transação de " + request.type().getValue() + ".");
		}
		return category;
	}
}
