package com.example.sportmate.record.authentification.signing;

import com.example.sportmate.DataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRequestDtoTest implements DataTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void userRequestDto_should_throw_exception_when_last_name_is_blank(final String lastName) {
        final UserRequestDto userRequest = new UserRequestDto(PROFILE_PICTURE, CONSENTS, lastName, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);

        final Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le nom est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("lastName");

        assertThat(violation.getInvalidValue())
                .isEqualTo(lastName);
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void sportRequestDto_should_throw_exception_when_first_name_is_blank(final String firstName) {
        final UserRequestDto userRequest = new UserRequestDto(PROFILE_PICTURE, CONSENTS, LAST_NAME, firstName, GENRE, BIRTHDAY, MOBILE);

        final Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserRequestDto> violation = violations.iterator().next();

        assertThat(violation.getMessage())
                .isEqualTo("Le prénom est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("firstName");

        assertThat(violation.getInvalidValue())
                .isEqualTo(firstName);
    }

    @Test
    void sportRequestDto_should_throw_exception_when_mobile_phone_is_null() {
        final UserRequestDto userRequest = new UserRequestDto(PROFILE_PICTURE, CONSENTS, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, null);

        final Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserRequestDto> violation = violations.iterator().next();

        assertThat(violation.getMessage())
                .isEqualTo("Le numéro de téléphone est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("mobilePhone");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void sportRequestDto_should_throw_exception_when_mobile_phone_not_respect_regex() {
        final UserRequestDto userRequest = new UserRequestDto(PROFILE_PICTURE, CONSENTS, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, "1234");

        final Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserRequestDto> violation = violations.iterator().next();

        assertThat(violation.getMessage())
                .isEqualTo("Le numéro de téléphone ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("mobilePhone");

        assertThat(violation.getInvalidValue())
                .isEqualTo("1234");
    }
}