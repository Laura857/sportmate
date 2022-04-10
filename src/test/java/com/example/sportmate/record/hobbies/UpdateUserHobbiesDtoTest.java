package com.example.sportmate.record.hobbies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UpdateUserHobbiesDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void updateUserHobbiesDto_should_throw_exception_when_hobbies_is_empty() {
        final UpdateUserHobbiesDto updateUserHobbiesDto = new UpdateUserHobbiesDto(emptyList());

        final Set<ConstraintViolation<UpdateUserHobbiesDto>> violations = validator.validate(updateUserHobbiesDto);

        assertThat(violations)
                .hasSize(1);

        final ConstraintViolation<UpdateUserHobbiesDto> violation = violations.iterator().next();
        assertThat(violation.getMessage())
                .isEqualTo("Au moins un hobbies doit être renseigné.");

        assertThat(violation.getPropertyPath().toString())
                .hasToString("hobbies");

        assertThat(violation.getInvalidValue())
                .isEqualTo(emptyList());
    }
}