package fintrack.finance_service.repository;

import fintrack.finance_service.model.Transaction;
import fintrack.finance_service.model.TransactionType;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("""
			select t from Transaction t join fetch t.category c
			where (:type is null or t.type = :type)
			  and (:categoryId is null or c.id = :categoryId)
			  and lower(t.description) like lower(concat('%', :search, '%'))
			order by t.transactionDate desc, t.id desc
			""")
	List<Transaction> search(@Param("type") TransactionType type,
			@Param("categoryId") Long categoryId,
			@Param("search") String search);

	@Query("select coalesce(sum(t.amount), 0) from Transaction t where t.type = :type")
	BigDecimal sumByType(@Param("type") TransactionType type);

	@EntityGraph(attributePaths = "category")
	List<Transaction> findTop5ByOrderByTransactionDateDescIdDesc();

	boolean existsByCategoryId(Long categoryId);
}
