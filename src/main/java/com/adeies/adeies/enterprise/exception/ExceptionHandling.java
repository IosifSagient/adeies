package com.adeies.adeies.enterprise.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(value = {ValidationFaultException.class})
    public ResponseEntity<Object> handleCustomException( ValidationFaultException ex){
         ErrorsPattern errorsPattern = new ErrorsPattern(
                 ex.getErrorCode(),
                 ex.getErrorDescription()
         );
        return new ResponseEntity<>(errorsPattern, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleBadObject( InvalidDataAccessApiUsageException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * DB Constraints Handler.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach((error) -> {
            errors.put(error.getPropertyPath().toString(), error.getMessageTemplate());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleBadObject( RuntimeException ex){
        return new ResponseEntity<>("Oopsie, something went wrong.", HttpStatus.BAD_REQUEST);
    }
}
