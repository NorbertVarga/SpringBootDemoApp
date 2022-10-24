package com.NorbertVarga.SpringBootDemoApp.errorHandling;

import org.h2.jdbc.JdbcSQLDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
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

    //  **  Validation errors and JSON related  //////////////////////////////
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        logger.error("** VALIDATION ERROR **");
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            logger.error("** " + fieldError.getField() + ", RV: " + fieldError.getRejectedValue());
        }
        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("JSON PARSE ERROR: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BAD_REQUEST",
                "Ups, something went wrong...",
                "Sorry, we can't tell you what was the problem.");
        return new ResponseEntity<>(body, status);
    }
    /////////////////////////////////////////////////////////

    //  **  DATABASE   ////////////////////////////////
    // These exceptions related to Hibernate Entity level validation, or jpa, or jdbc
    // (Data cannot be saved to the database)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Database validation error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BAD_REQUEST",
                "Ups, something went wrong...",
                "Sorry, we can't tell you what was the problem.");
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(JdbcSQLDataException.class)
    protected ResponseEntity<ApiError> handleJdbcSQLDataException(JdbcSQLDataException ex) {
        logger.error("Database validation error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BAD_REQUEST",
                "Ups, something went wrong...",
                "Sorry, we can't tell you what was the problem.");
        return new ResponseEntity<>(body, status);
    }
    /////////////////////////////////////////////////////////

    //  **  BUSINESS   ////////////////////
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("Entity not found error: ", ex);
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError body = new ApiError(
                "NOT_FOUND",
                "Entity is missing for the operation",
                ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<ApiError> handleEntityExistException(EntityExistsException ex) {
        logger.error("Entity already exist error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BAD_REQUEST",
                "Entity cannot be duplicated.",
                ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UserBalanceNotEnoughException.class)
    protected ResponseEntity<ApiError> handleUserBalanceNotEnoughException(UserBalanceNotEnoughException ex) {
        logger.error("User don't have enough money to make purchase: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "BALANCE_NOT_ENOUGH",
                "User don't have enough money to make the purchase",
                ex.getMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(SessionAuthenticationException.class)
    protected ResponseEntity<ApiError> handleSessionAuthenticationException(SessionAuthenticationException ex) {
        logger.error("The user in the cart does not match the logged in user: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = new ApiError(
                "INVALID_SESSION",
                "The user in the cart does not match the logged in user",
                ex.getMessage());
        return new ResponseEntity<>(body, status);
    }
    ////////////////////////////////////////////////////////////

    //  **  UTILS  ////////////////////////////////
    private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
        ValidationError validationError = new ValidationError();
        for (FieldError fieldError : fieldErrors) {
            validationError.addFieldError(fieldError.getField(), messageSource.getMessage(fieldError, Locale.getDefault()));
        }
        return validationError;
    }

}
