package com.edj.teamoop.exception;

import com.edj.teamoop.exception.handler.GlobalExceptionHandler;
import com.edj.teamoop.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {
    @Test
    void testHandleGlobalException() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        Exception exception = new Exception("Et boom !");
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebRequest webRequest = new ServletWebRequest(request);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleGlobalException(exception, webRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        ErrorResponse errorResponse = responseEntity.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.getMessage()).isEqualTo("Internal Error");
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void testdHandleIllegalArgumentException() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebRequest webRequest = new ServletWebRequest(request);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleIllegalArgumentException(exception, webRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponse errorResponse = responseEntity.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.getMessage()).isEqualTo("Internal Error");
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
