package com.adeies.adeies.enterprise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(value = {ValidationFaultException.class})
    public ResponseEntity<Object> handleException( ValidationFaultException ex){
         ErrorsPattern errorsPattern = new ErrorsPattern(
                 ex.getErrorCode(),
                 ex.getErrorDescription()
         );
        return new ResponseEntity<>(errorsPattern, HttpStatus.BAD_REQUEST);
    }
}
