package com.example.sportmate.record;

import com.example.sportmate.DataTest;
import com.example.sportmate.record.activity.ActivityResponseDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityResponseDtoTest implements DataTest  {

    @ParameterizedTest
    @MethodSource("compareDate")
    void compare(final LocalDateTime otherDate, final int response){
        final ActivityResponseDto activityWithTodayActivityDate = new ActivityResponseDto(ID, IS_EVENT, ACTIVITY_NAME,
                ACTIVITY_DATE, ID, ADDRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, ACTIVITY_NAME, CONTACT, DESCRIPTION);

        final ActivityResponseDto otherActivity = new ActivityResponseDto(ID, IS_EVENT, ACTIVITY_NAME,
                otherDate, ID, ADDRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, ACTIVITY_NAME, CONTACT, DESCRIPTION);

        final ActivityResponseDto.DateComparator dateComparator = new ActivityResponseDto.DateComparator();
        assertThat(dateComparator.compare(activityWithTodayActivityDate, otherActivity))
                .isEqualTo(response);
    }

    private static Stream<Arguments> compareDate() {
        return Stream.of(
                Arguments.of(ACTIVITY_DATE, 0),
                Arguments.of(ACTIVITY_DATE.minusDays(1), -1),
                Arguments.of(ACTIVITY_DATE.plusDays(1), 1)
        );
    }

}