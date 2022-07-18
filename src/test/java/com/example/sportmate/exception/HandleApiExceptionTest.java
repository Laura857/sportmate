package com.example.sportmate.exception;

import com.example.sportmate.record.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.reflect.Method;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest
class HandleApiExceptionTest {
    private final String ERROR_MESSAGE = "Error message";
    @Autowired
    HandleApiException handleApiException;

    @Test
    void handleNotFoundException(){
        assertThat(handleApiException.handleNotFoundException(new NotFoundException(ERROR_MESSAGE)))
                .isEqualTo(new ResponseEntity<>(new ErrorResponse(ERROR_MESSAGE), NOT_FOUND));
    }

    @Test
    void handleBadRequestException(){
        assertThat(handleApiException.handleBadRequestException(new BadRequestException(ERROR_MESSAGE)))
                .isEqualTo(new ResponseEntity<>(new ErrorResponse(ERROR_MESSAGE), BAD_REQUEST));
    }

    @Test
    void handleAuthenticationException(){
        assertThat(handleApiException.handleAuthenticationException(new AuthenticationException(ERROR_MESSAGE)))
                .isEqualTo(new ResponseEntity<>(new ErrorResponse(ERROR_MESSAGE), BAD_REQUEST));
    }

    @Test
    void handleInvalidFormatException(){
        assertThat(handleApiException.handleInvalidFormatException(new Exception(ERROR_MESSAGE)))
                .isEqualTo(new ResponseEntity<>(new ErrorResponse(ERROR_MESSAGE), BAD_REQUEST));
    }

    @Test
    void handleMethodArgumentNotValid() throws NoSuchMethodException {
        final BindingResult bindingResult = mock(BindingResult.class);
        bindingResult.getAllErrors();

        final HandleApiExceptionTest handleApiExceptionTest =  new HandleApiExceptionTest();
        final Method method = handleApiExceptionTest.getClass().getMethod("doSomething");
        final MethodParameter methodParameter = new MethodParameter(method, -1);

        final MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(methodParameter, bindingResult);

        when(bindingResult.getAllErrors())
                .thenReturn(singletonList(new ObjectError("ObjectName", ERROR_MESSAGE)));

        assertThat(handleApiException.handleMethodArgumentNotValid(methodArgumentNotValidException))
                .isEqualTo(new ResponseEntity<>(singletonList(new ErrorResponse(ERROR_MESSAGE)), BAD_REQUEST));
    }

    public void doSomething() {
    }
}