package com.example.sportmate.record.authentification.login;

import com.example.sportmate.DataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoginRequestDtoTest implements DataTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void loginRequestDto_should_throw_exception_when_email_is_null() {
        final LoginRequestDto loginRequest = new LoginRequestDto(null, PASSWORD);

        final Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<LoginRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("L'email est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("email");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void loginRequestDto_should_throw_exception_when_email_not_respect_regex() {
        final LoginRequestDto loginRequest = new LoginRequestDto("Un email", PASSWORD);

        final Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<LoginRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("L'email ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("email");

        assertThat(violation.getInvalidValue())
                .isEqualTo("Un email");
    }

    @Test
    void loginRequestDto_should_throw_exception_when_password_is_null() {
        final LoginRequestDto loginRequest = new LoginRequestDto(EMAIL, null);

        final Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<LoginRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le mot de passe est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("password");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void loginRequestDto_should_throw_exception_when_password_not_respect_regex() {
        final LoginRequestDto loginRequest = new LoginRequestDto(EMAIL, "unMotDePasseBienSolide");

        final Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<LoginRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le mot de passe ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("password");

        assertThat(violation.getInvalidValue())
                .isEqualTo("unMotDePasseBienSolide");
    }

}