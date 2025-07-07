package br.com.fitnessconsultant.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class PersonalGymApiExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors UserNotFoundException(UserNotFoundException e) {
        return new ApiErrors(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationError>> ConstraintViolationException(ConstraintViolationException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.add(new ValidationError(field, message, HttpStatus.BAD_REQUEST.value()));
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ValidationError>> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = e.getFieldErrors()
                .stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage(), HttpStatus.BAD_REQUEST.value()))
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors RecordNotFoundException(RecordNotFoundException e) {
        return new ApiErrors(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({
            ApiErrorException.class,
            SendEmailException.class,
            InvalidTokenException.class,
            InfoAlreadyExistsException.class,
            RoleInvalidException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors ApiErrorException(Exception e) {
        return new ApiErrors(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors JWTVerificationException() {
        return new ApiErrors("O token JWT está inválido ou expirado", HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors BadCredentialsException() {
        return new ApiErrors("Usuário ou senha inválida", HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors AccountStatusException() {
        return new ApiErrors("A conta está bloqueada", HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors AccessDeniedException() {
        return new ApiErrors("Você não está autorizado a acessar este recurso", HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors SignatureException() {
        return new ApiErrors("A assinatura JWT é inválida", HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(JWTCreationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors JWTCreationException() {
        return new ApiErrors("Erro ao gerar token", HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors HttpMessageNotReadableException() {
        return new ApiErrors("Erro na request", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors DataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ApiErrors("Violação de integridade de dados", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors AuthenticationException() {
        return new ApiErrors("Erro ao autenticar usuário", HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(MismatchedInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMismatchedInput(MismatchedInputException ex) {
        return new ApiErrors("Um ou mais campos foram preenchidos com o tipo errado. Verifique os dados enviados.", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleInvalidFormat(InvalidFormatException ex) {
        return new ApiErrors(
                String.format("O valor '%s' não é válido para o campo esperado. Verifique o tipo de dado enviado.", ex.getValue()),
                HttpStatus.BAD_REQUEST.value());
    }
}
