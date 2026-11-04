package fintrack.finance_service.controller;

import fintrack.finance_service.dto.TransactionRequest;
import fintrack.finance_service.dto.TransactionResponse;
import fintrack.finance_service.model.TransactionType;
import fintrack.finance_service.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/** Filtros opcionais: ?type=income|expense &categoryId=1 &search=mercado */
	@GetMapping
	public List<TransactionResponse> list(
			@RequestParam(required = false) String type,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) String search) {
		TransactionType parsedType = (type == null || type.isBlank() || type.equalsIgnoreCase("all"))
				? null
				: TransactionType.fromValue(type);
		return transactionService.list(parsedType, categoryId, search);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionResponse create(@Valid @RequestBody TransactionRequest request) {
		return transactionService.create(request);
	}

	@PutMapping("/{id}")
	public TransactionResponse update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
		return transactionService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		transactionService.delete(id);
	}
}
