package com.example.sportmate.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@SpringBootTest
class HandleApiExceptionTest {
    @Autowired
    HandleApiException handleApiException;

    @Test
    void handleNotFoundException(){
        final MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        final ServletWebRequest servletWebRequest = new ServletWebRequest(servletRequest);

        assertThat(handleApiException.handleNotFoundException(new RuntimeException("Error"), servletWebRequest))
                .isEqualTo(new ResponseEntity<>("Error", HttpStatus.NOT_FOUND));
    }

    @Test
    void handleBadRequestException(){
        final MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        final ServletWebRequest servletWebRequest = new ServletWebRequest(servletRequest);

        assertThat(handleApiException.handleBadRequestException(new RuntimeException("Error"), servletWebRequest))
                .isEqualTo(new ResponseEntity<>("Error", BAD_REQUEST));
    }

    @Test
    void handleAuthenticationException(){
        final MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        final ServletWebRequest servletWebRequest = new ServletWebRequest(servletRequest);

        assertThat(handleApiException.handleAuthenticationException(new RuntimeException("Error"), servletWebRequest))
                .isEqualTo(new ResponseEntity<>("Error", BAD_REQUEST));
    }

    @Test
    void handleInvalidFormatException(){
        final MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        final ServletWebRequest servletWebRequest = new ServletWebRequest(servletRequest);

        assertThat(handleApiException.handleInvalidFormatException(new RuntimeException("Error"), servletWebRequest))
                .isEqualTo(new ResponseEntity<>("Error", BAD_REQUEST));
    }

    @Test
    void handleMethodArgumentNotValid() throws NoSuchMethodException {

        final MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        final ServletWebRequest servletWebRequest = new ServletWebRequest(servletRequest);
        final BindingResult bindingResult = mock(BindingResult.class);

        final HandleApiExceptionTest handleApiExceptionTest =  new HandleApiExceptionTest();
        final Method method = handleApiExceptionTest.getClass().getMethod("doSomething");
        final MethodParameter methodParameter = new MethodParameter(method, -1);

        final MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(methodParameter, bindingResult);

        assertThat(handleApiException.handleMethodArgumentNotValid(methodArgumentNotValidException, new HttpHeaders(), BAD_REQUEST, servletWebRequest))
                .isEqualTo(new ResponseEntity<>("Validation failed for argument [-1] in public void com.example.sportmate.exception.HandleApiExceptionTest.doSomething(): ", BAD_REQUEST));
    }

    public void doSomething() {
    }
}