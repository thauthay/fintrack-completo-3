package fintrack.finance_service.dto;

import fintrack.finance_service.model.Category;
import fintrack.finance_service.model.TransactionType;

public record CategoryResponse(Long id, String name, TransactionType type) {

	public static CategoryResponse from(Category category) {
		return new CategoryResponse(category.getId(), category.getName(), category.getType());
	}
}
