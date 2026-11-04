package fintrack.finance_service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** No JSON o frontend usa "income" / "expense" em minúsculo. */
public enum TransactionType {
	INCOME("income"),
	EXPENSE("expense");

	private final String value;

	TransactionType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static TransactionType fromValue(String value) {
		for (TransactionType type : values()) {
			if (type.value.equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Tipo inválido: " + value + " (use 'income' ou 'expense')");
	}
}
