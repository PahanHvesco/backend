package com.myapp.backend.advice;


import com.myapp.backend.advice.DefaultAdvice.ExceptionMessage;
import com.myapp.backend.exception.BadRequestErrorException;
import com.myapp.backend.exception.InvalidDataException;
import com.myapp.backend.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    public void testHandleResourceInvalidData() {
        InvalidDataException exception = new InvalidDataException("Resource not found");
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

    @Test
    public void testExceptionMessage() {
        DefaultAdvice.ExceptionMessage exceptionMessage = new DefaultAdvice.ExceptionMessage(404, "Not Found");
        assertEquals(404, exceptionMessage.getHttpStatus());
        assertEquals("Not Found", exceptionMessage.getMessage());
    }

    @Test
    public void testEquality() {
        // Создаем два объекта ExceptionMessage с одинаковыми значениями
        DefaultAdvice.ExceptionMessage message1 = new DefaultAdvice.ExceptionMessage(404, "Not Found");
        DefaultAdvice.ExceptionMessage message2 = new DefaultAdvice.ExceptionMessage(404, "Not Found");

        // Проверяем, что объекты равны
        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }

    @Test
    public void testInequality() {
        // Создаем два объекта ExceptionMessage с разными значениями
        DefaultAdvice.ExceptionMessage message1 = new DefaultAdvice.ExceptionMessage(404, "Not Found");
        DefaultAdvice.ExceptionMessage message2 = new DefaultAdvice.ExceptionMessage(500, "Internal Server Error");

        // Проверяем, что объекты не равны
        assertNotEquals(message1, message2);
        assertNotEquals(message1.hashCode(), message2.hashCode());
    }

    @Test
    public void testToString() {
        // Создаем объект ExceptionMessage
        DefaultAdvice.ExceptionMessage message = new DefaultAdvice.ExceptionMessage(404, "Not Found");

        // Проверяем метод toString()
        assertEquals("DefaultAdvice.ExceptionMessage(httpStatus=404, message=Not Found)", message.toString());
    }
}
