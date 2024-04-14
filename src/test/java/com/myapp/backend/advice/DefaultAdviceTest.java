package com.myapp.backend.advice;


import com.myapp.backend.advice.DefaultAdvice.ExceptionMessage;
import com.myapp.backend.exception.BadRequestErrorException;
import com.myapp.backend.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class DefaultAdviceTest {

    @InjectMocks
    private DefaultAdvice defaultAdvice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleBadRequestErrorException() {
        BadRequestErrorException exception = new BadRequestErrorException("Bad request");
        ExceptionMessage expected = new ExceptionMessage(HttpStatus.BAD_REQUEST.value(), "Bad request");
        assertEquals(expected, defaultAdvice.handleBadRequestErrorException(exception).getBody());
    }

    @Test
    public void testHandleInternalServerErrorException() {
        RuntimeException exception = new RuntimeException("Internal Server Error");
        ExceptionMessage expected = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        assertEquals(expected, defaultAdvice.handleInternalServerErrorException(exception).getBody());
    }

    @Test
    public void testHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
        ExceptionMessage expected = new ExceptionMessage(HttpStatus.NOT_FOUND.value(), "Resource not found");
        assertEquals(expected, defaultAdvice.handleResourceNotFoundException(exception).getBody());
    }

    @Test
    public void testHandleNoHandlerFoundException() {
        NoHandlerFoundException exception = new NoHandlerFoundException("GET", "/path", null);
        ExceptionMessage expected = new ExceptionMessage(HttpStatus.NOT_FOUND.value(), "No endpoint GET /path.");
        assertEquals(expected, defaultAdvice.handleNoHandlerFoundException(exception).getBody());
    }

    @Test
    public void testHandleMethodNotAllowed() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("POST");
        ExceptionMessage expected = new ExceptionMessage(HttpStatus.METHOD_NOT_ALLOWED.value(), "Request method 'POST' is not supported");
        assertEquals(expected, defaultAdvice.handleMethodNotAllowed(exception).getBody());
    }

    @Test
    public void testHandleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(null, null, null, null, null);
        ExceptionMessage expected = new ExceptionMessage(HttpStatus.BAD_REQUEST.value(), "Invalid input!");
        assertEquals(expected, defaultAdvice.handleMethodArgumentTypeMismatchException(exception).getBody());
    }
}