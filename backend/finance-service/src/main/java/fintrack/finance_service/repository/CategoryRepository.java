package fintrack.finance_service.repository;

import fintrack.finance_service.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByOrderByNameAsc();

	boolean existsByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
