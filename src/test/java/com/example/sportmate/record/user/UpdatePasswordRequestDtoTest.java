package com.example.sportmate.record.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.example.sportmate.DataTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UpdatePasswordRequestDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void updatePasswordRequestDto_should_throw_exception_when_old_password_is_null() {
        final UpdatePasswordRequestDto updatePasswordRequestDtoTest = new UpdatePasswordRequestDto(null, PASSWORD);
        final Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(updatePasswordRequestDtoTest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UpdatePasswordRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("L'ancien mot de passe est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("oldPassword");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void updatePasswordRequestDto_should_throw_exception_when_old_password_not_respect_regex() {
        final UpdatePasswordRequestDto updatePasswordRequestDtoTest = new UpdatePasswordRequestDto("unMotDePasse", PASSWORD);
        final Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(updatePasswordRequestDtoTest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UpdatePasswordRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("L'ancien mot de passe ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("oldPassword");

        assertThat(violation.getInvalidValue())
                .isEqualTo("unMotDePasse");
    }

    @Test
    void updatePasswordRequestDto_should_throw_exception_when_new_password_is_null() {
        final UpdatePasswordRequestDto updatePasswordRequestDtoTest = new UpdatePasswordRequestDto(PASSWORD, null);
        final Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(updatePasswordRequestDtoTest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UpdatePasswordRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le nouveau mot de passe est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("newPassword");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void updatePasswordRequestDto_should_throw_exception_when_new_password_not_respect_regex() {
        final UpdatePasswordRequestDto updatePasswordRequestDtoTest = new UpdatePasswordRequestDto(PASSWORD, "unMotDePasse");
        final Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(updatePasswordRequestDtoTest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UpdatePasswordRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le nouveau mot de passe ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("newPassword");

        assertThat(violation.getInvalidValue())
                .isEqualTo("unMotDePasse");
    }

}