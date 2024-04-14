package com.myapp.backend.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myapp.backend.exception.BadRequestErrorException;
import com.myapp.backend.exception.InvalidDataException;
import com.myapp.backend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Log4j2
public final class DefaultAdvice {
    @Data
    @AllArgsConstructor
    public static class ExceptionMessage {
        private int httpStatus;
        private String message;
    }

    @ExceptionHandler({HttpClientErrorException.class,
                       HttpMessageNotReadableException.class,
                       MethodArgumentNotValidException.class,
                       MissingServletRequestParameterException.class,
                       ConstraintViolationException.class,
                       JsonProcessingException.class,
                       BadRequestErrorException.class,
                       DateTimeParseException.class,
                       IllegalArgumentException.class,
                       InvalidDataException.class})
    public ResponseEntity<ExceptionMessage> handleBadRequestErrorException(final Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ExceptionMessage> handleInternalServerErrorException(final RuntimeException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ExceptionMessage> handleResourceNotFoundException(final RuntimeException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionMessage> handleNoHandlerFoundException(final NoHandlerFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionMessage> handleMethodNotAllowed(final Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.METHOD_NOT_ALLOWED.value(), exception.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessage> handleMethodArgumentTypeMismatchException(final Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.BAD_REQUEST.value(), "Invalid input!"), HttpStatus.BAD_REQUEST);
    }
}
