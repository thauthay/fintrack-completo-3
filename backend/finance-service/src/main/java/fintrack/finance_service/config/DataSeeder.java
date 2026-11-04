package fintrack.finance_service.config;

import fintrack.finance_service.model.Category;
import fintrack.finance_service.model.TransactionType;
import fintrack.finance_service.repository.CategoryRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Cria as categorias iniciais que o frontend já usa, só se a tabela estiver vazia. */
@Component
public class DataSeeder implements CommandLineRunner {

	private final CategoryRepository categoryRepository;

	public DataSeeder(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(String... args) {
		if (categoryRepository.count() > 0) {
			return;
		}
		categoryRepository.saveAll(List.of(
				new Category("Alimentação", TransactionType.EXPENSE),
				new Category("Transporte", TransactionType.EXPENSE),
				new Category("Moradia", TransactionType.EXPENSE),
				new Category("Salário", TransactionType.INCOME)));
	}
}
