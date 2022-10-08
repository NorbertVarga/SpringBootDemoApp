package com.NorbertVarga.SpringBootDemoApp.errorHandling;

import org.h2.jdbc.JdbcSQLDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // Validation errors throws "MethodArgumentNotValidException"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("A validation error occurred: ", ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    // These exceptions related to Hibernate Entity level validation, or jpa, or jdbc
    // (Data cannot be saved to the database)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Database validation error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BAD_REQUEST",
                "Ups, something went wrong...",
                "Sorry, we can't tell you what was the problem.");
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(JdbcSQLDataException.class)
    public ResponseEntity<ApiError> handleJdbcSQLDataException(JdbcSQLDataException ex) {
        logger.error("Database validation error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BAD_REQUEST",
                "Ups, something went wrong...",
                "Sorry, we can't tell you what was the problem.");
        return new ResponseEntity<>(body, status);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("User principal error: ", ex);
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError body = new ApiError(
                "NOT_FOUND",
                "Entity is missing for the operation",
                ex.getLocalizedMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiError> handleEntityExistException(EntityExistsException ex) {
        logger.error("User principal error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BAD_REQUEST",
                "Entity cannot be duplicated.",
                ex.getLocalizedMessage());
        return new ResponseEntity<>(body, status);
    }


    private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
        ValidationError validationError = new ValidationError();
        for (FieldError fieldError : fieldErrors) {
            validationError.addFieldError(fieldError.getField(), messageSource.getMessage(fieldError, Locale.getDefault()));
        }
        return validationError;
    }

}
