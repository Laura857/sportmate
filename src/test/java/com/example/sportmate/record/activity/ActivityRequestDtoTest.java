package com.example.sportmate.record.activity;

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
class ActivityRequestDtoTest implements DataTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void activityRequestDto_should_throw_exception_when_activity_date_is_null() {
        final ActivityRequestDto activityRequest = new ActivityRequestDto(false, ACTIVITY_NAME, null, ADDRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, CONTACT, DESCRIPTION);

        final Set<ConstraintViolation<ActivityRequestDto>> violations = validator.validate(activityRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<ActivityRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le date est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("activityDate");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void activityRequestDto_should_throw_exception_when_address_is_blank(final String address) {
        final ActivityRequestDto activityRequest = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, address,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, CONTACT, DESCRIPTION);

        final Set<ConstraintViolation<ActivityRequestDto>> violations = validator.validate(activityRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<ActivityRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("L'addresse est obligatoire'.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("address");

        assertThat(violation.getInvalidValue())
                .isEqualTo(address);
    }

    @Test
    void activityRequestDto_should_throw_exception_when_sport_is_null() {
        final ActivityRequestDto activityRequest = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADDRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, null, LEVEL_NAME, CONTACT, DESCRIPTION);

        final Set<ConstraintViolation<ActivityRequestDto>> violations = validator.validate(activityRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<ActivityRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le sport est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("sport");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void activityRequestDto_should_throw_exception_when_level_is_null() {
        final ActivityRequestDto activityRequest = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADDRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, null, CONTACT, DESCRIPTION);

        final Set<ConstraintViolation<ActivityRequestDto>> violations = validator.validate(activityRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<ActivityRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le niveau est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("activityLevel");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void activityRequestDto_should_throw_exception_when_contact_is_null() {
        final ActivityRequestDto activityRequest = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADDRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, null, DESCRIPTION);

        final Set<ConstraintViolation<ActivityRequestDto>> violations = validator.validate(activityRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<ActivityRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le contact est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("contact");

        assertThat(violation.getInvalidValue())
                .isNull();
    }

    @Test
    void activityRequestDto_should_throw_exception_when_contact_not_respect_regex() {
        final ActivityRequestDto activityRequest = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADDRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, "1234", DESCRIPTION);

        final Set<ConstraintViolation<ActivityRequestDto>> violations = validator.validate(activityRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<ActivityRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le numéro de téléphone ne respecte pas le bon format.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("contact");

        assertThat(violation.getInvalidValue())
                .isEqualTo("1234");
    }
}