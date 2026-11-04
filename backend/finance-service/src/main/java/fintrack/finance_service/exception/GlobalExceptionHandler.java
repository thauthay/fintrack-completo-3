package fintrack.finance_service.exception;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ProblemDetail notFound(ResourceNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(ConflictException.class)
	public ProblemDetail conflict(ConflictException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
	}

	@ExceptionHandler(BusinessRuleException.class)
	public ProblemDetail businessRule(BusinessRuleException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ProblemDetail badArgument(IllegalArgumentException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ProblemDetail unreadable(HttpMessageNotReadableException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
				"Corpo da requisição inválido (confira tipo, datas e valores).");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail validation(MethodArgumentNotValidException ex) {
		String detail = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining("; "));
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
	}
}
