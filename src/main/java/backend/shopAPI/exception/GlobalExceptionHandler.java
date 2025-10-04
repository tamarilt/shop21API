package backend.shopAPI.exception;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import backend.shopAPI.DTO.errors.ErrorResponse;
import backend.shopAPI.DTO.errors.ValidationError;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMehodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ValidationError> errors = fieldErrors.stream().map(error -> new ValidationError(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse(400, errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadUUID(MethodArgumentTypeMismatchException ex) {
        ValidationError error = new ValidationError(ex.getName(), "Неправильный формат ID");
        ErrorResponse errorResponse = new ErrorResponse(400, Arrays.asList(error));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ValidationError error = new ValidationError("id", "Неправильный формат ID");
        ErrorResponse errorResponse = new ErrorResponse(400, Arrays.asList(error));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Некорректные данные: {}", ex.getMessage());
        ValidationError error = new ValidationError("data", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(400, Arrays.asList(error));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex) {
        log.warn("Недостаточный остаток товара: {}", ex.getMessage());
        ValidationError error = new ValidationError("stock", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(400, Arrays.asList(error));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.warn("Данные не найдены: {}", ex.getMessage());
        ValidationError error = new ValidationError("id", "Запись не найдена");
        ErrorResponse errorResponse = new ErrorResponse(404, Arrays.asList(error));
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Произошла неожиданная ошибка: ", ex);
        ValidationError error = new ValidationError("server", "Внутренняя ошибка сервера: " + ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(500, Arrays.asList(error));
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
