package br.com.freelance_management_api.controller.excetion;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    private StandardError createStandardError(HttpStatus status, String error, String message, HttpServletRequest request) {
        return new StandardError(Instant.now(), status.value(), error, message, request.getRequestURI());
    }

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(ControllerNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createStandardError(HttpStatus.NOT_FOUND, "Entidade não encontrada", e.getMessage(), request));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createStandardError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e.getMessage(), request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        ValidationError err = new ValidationError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                "Erro de validação", "Erro ao validar campos do formulário", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<StandardError> handleMissingPathVariable(MissingPathVariableException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createStandardError(HttpStatus.BAD_REQUEST, "Parâmetro obrigatório ausente", e.getMessage(), request));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String message = "O parametro '" + e.getName() + "' deve ser um UUID valido.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createStandardError(HttpStatus.BAD_REQUEST, "Parametro invalido", message, request));
    }

}
