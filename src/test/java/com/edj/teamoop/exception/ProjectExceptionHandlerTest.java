package com.edj.teamoop.exception;

import com.edj.teamoop.exception.handler.ProjectExceptionHandler;
import com.edj.teamoop.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectExceptionHandlerTest {
    @Test
    void testHandleProjectNotFoundException() {
        ProjectExceptionHandler exceptionHandler = new ProjectExceptionHandler();
        ProjectNotFoundException exception = new ProjectNotFoundException("The project with ID 123 does not exist");
        MockHttpServletRequest request = new MockHttpServletRequest();
        WebRequest webRequest = new ServletWebRequest(request);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleProjectNotFoundException(exception, webRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ErrorResponse errorResponse = responseEntity.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.getMessage()).isEqualTo("The project with ID 123 does not exist");
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
