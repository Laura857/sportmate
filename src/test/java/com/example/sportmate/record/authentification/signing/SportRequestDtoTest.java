package com.example.sportmate.record.authentification.signing;

import com.example.sportmate.DataTest;
import org.junit.jupiter.api.BeforeEach;
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
class SportRequestDtoTest implements DataTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void sportRequestDto_should_throw_exception_when_name_is_blank(final String sportName) {
        final SportRequestDto sportRequest = new SportRequestDto(sportName, LEVEL_NAME);

        final Set<ConstraintViolation<SportRequestDto>> violations = validator.validate(sportRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<SportRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le nom du sport est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("name");

        assertThat(violation.getInvalidValue())
                .isEqualTo(sportName);
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void sportRequestDto_should_throw_exception_when_level_is_blank(final String level) {
        final SportRequestDto sportRequest = new SportRequestDto(SPORT_NAME, level);

        final Set<ConstraintViolation<SportRequestDto>> violations = validator.validate(sportRequest);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<SportRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Le niveau du sport est obligatoire.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("level");

        assertThat(violation.getInvalidValue())
                .isEqualTo(level);
    }

}