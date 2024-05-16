package br.com.personalgymapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class PersonalGymApiExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors UserNotFoundException(UserNotFoundException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors UserNotFoundException(ConstraintViolationException e) {
        List<String> errors = e
                .getConstraintViolations()
                .stream()
                .map(erro -> erro.getMessageTemplate())
                .toList();
        return new ApiErrors(errors);
    }

    @ExceptionHandler(InfoAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors UserNotFoundException(InfoAlreadyExistsException e) {
        return new ApiErrors(e.getMessage());
    }

}