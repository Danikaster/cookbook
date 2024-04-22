package com.cookbook.cookbook.exceptions;

import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDetails> serverException(
            final RuntimeException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> resourceNotFoundException(
            final ResourceNotFoundException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpClientErrorException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            EntityNotFoundException.class,
            ConstraintViolationException.class,
            JsonProcessingException.class,
            DateTimeParseException.class,
            IllegalArgumentException.class,
            BadRequestException.class,
            JsonParseException.class})
    public ResponseEntity<ExceptionDetails> badRequestException(
            final Exception exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.BAD_REQUEST);
    }

}
