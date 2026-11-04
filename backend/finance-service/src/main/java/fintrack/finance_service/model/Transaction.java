package fintrack.finance_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120)
	private String description;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private TransactionType type;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "transaction_date", nullable = false)
	private LocalDate transactionDate;

	protected Transaction() {
	}

	public Transaction(String description, BigDecimal amount, TransactionType type,
			Category category, LocalDate transactionDate) {
		this.description = description;
		this.amount = amount;
		this.type = type;
		this.category = category;
		this.transactionDate = transactionDate;
	}

	public Long getId() { return id; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public BigDecimal getAmount() { return amount; }
	public void setAmount(BigDecimal amount) { this.amount = amount; }
	public TransactionType getType() { return type; }
	public void setType(TransactionType type) { this.type = type; }
	public Category getCategory() { return category; }
	public void setCategory(Category category) { this.category = category; }
	public LocalDate getTransactionDate() { return transactionDate; }
	public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
}
