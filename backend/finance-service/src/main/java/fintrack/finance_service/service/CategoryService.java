package fintrack.finance_service.service;

import fintrack.finance_service.dto.CategoryRequest;
import fintrack.finance_service.dto.CategoryResponse;
import fintrack.finance_service.exception.ConflictException;
import fintrack.finance_service.exception.ResourceNotFoundException;
import fintrack.finance_service.model.Category;
import fintrack.finance_service.repository.CategoryRepository;
import fintrack.finance_service.repository.TransactionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final TransactionRepository transactionRepository;

	public CategoryService(CategoryRepository categoryRepository,
			TransactionRepository transactionRepository) {
		this.categoryRepository = categoryRepository;
		this.transactionRepository = transactionRepository;
	}

	@Transactional(readOnly = true)
	public List<CategoryResponse> list() {
		return categoryRepository.findAllByOrderByNameAsc().stream()
				.map(CategoryResponse::from)
				.toList();
	}

	@Transactional
	public CategoryResponse create(CategoryRequest request) {
		String name = request.name().trim();
		if (categoryRepository.existsByNameIgnoreCase(name)) {
			throw new ConflictException("Já existe uma categoria chamada \"" + name + "\".");
		}
		return CategoryResponse.from(categoryRepository.save(new Category(name, request.type())));
	}

	@Transactional
	public CategoryResponse update(Long id, CategoryRequest request) {
		Category category = findOrThrow(id);
		String name = request.name().trim();

		if (categoryRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
			throw new ConflictException("Já existe uma categoria chamada \"" + name + "\".");
		}
		// Mudar o tipo de uma categoria em uso deixaria transações inconsistentes.
		if (category.getType() != request.type() && transactionRepository.existsByCategoryId(id)) {
			throw new ConflictException(
					"Não é possível mudar o tipo de uma categoria que já possui transações.");
		}

		category.setName(name);
		category.setType(request.type());
		return CategoryResponse.from(category);
	}

	@Transactional
	public void delete(Long id) {
		Category category = findOrThrow(id);
		if (transactionRepository.existsByCategoryId(id)) {
			throw new ConflictException(
					"Não é possível excluir \"" + category.getName() + "\": existem transações nessa categoria.");
		}
		categoryRepository.delete(category);
	}

	private Category findOrThrow(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria " + id + " não encontrada."));
	}
}
