package com.example.sportmate.record.user;

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
class UserDataDtoTest implements DataTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userDataDto_should_throw_exception_when_email_is_blank() {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                null,
                LAST_NAME,
                FIRST_NAME,
                GENRE,
                BIRTHDAY,
                MOBILE);
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("L'email est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("email");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void userDataDto_should_throw_exception_when_email_not_respect_regex() {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                "Un email",
                LAST_NAME,
                FIRST_NAME,
                GENRE,
                BIRTHDAY,
                MOBILE);
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("L'email ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("email");

        assertThat(violation.getInvalidValue())
                .isEqualTo("Un email");
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void userDataDto_should_throw_exception_when_last_name_is_blank(final String lastName) {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                EMAIL,
                lastName,
                FIRST_NAME,
                GENRE,
                BIRTHDAY,
                MOBILE);
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
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
    void userDataDto_should_throw_exception_when_first_name_is_blank(final String firstName) {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                EMAIL,
                LAST_NAME,
                firstName,
                GENRE,
                BIRTHDAY,
                MOBILE);
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le prénom est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("firstName");

        assertThat(violation.getInvalidValue())
                .isEqualTo(firstName);
    }

    @Test
    void userDataDto_should_throw_exception_when_genre_is_null() {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                EMAIL,
                LAST_NAME,
                FIRST_NAME,
                null,
                BIRTHDAY,
                MOBILE);
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le genre est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("genre");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void userDataDto_should_throw_exception_when_birthday_is_null() {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                EMAIL,
                LAST_NAME,
                FIRST_NAME,
                GENRE,
                null,
                MOBILE);
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("La date d'anniversaire est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("birthday");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void userDataDto_should_throw_exception_when_mobile_phone_not_respect_regex() {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                EMAIL,
                LAST_NAME,
                FIRST_NAME,
                GENRE,
                BIRTHDAY,
                "1234");
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le numéro de téléphone ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("mobilePhone");

        assertThat(violation.getInvalidValue())
                .isEqualTo("1234");
    }

    @Test
    void userDataDto_should_throw_exception_when_mobile_phone_is_null() {
        final UserDataDto userDataDto = new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                EMAIL,
                LAST_NAME,
                FIRST_NAME,
                GENRE,
                BIRTHDAY,
                null);
        final Set<ConstraintViolation<UserDataDto>> violations = validator.validate(userDataDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UserDataDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le numéro de téléphone est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("mobilePhone");

        assertThat(violation.getInvalidValue())
                .isNull();
    }
}