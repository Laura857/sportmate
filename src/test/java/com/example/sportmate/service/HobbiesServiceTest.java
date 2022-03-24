package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.repository.HobbiesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.sportmate.enumeration.SportEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class HobbiesServiceTest implements DataTest {
    @Autowired
    HobbiesService hobbiesService;

    @MockBean
    HobbiesRepository hobbiesRepository;

    @Test
    void getUserHobbies_should_return_emptyList_when_no_sports_are_found_in_database() {
        when(hobbiesRepository.findUserHobbies(ID))
                .thenReturn(emptyList());

        assertThat(hobbiesService.getUserHobbies(ID))
                .isEmpty();
    }

    @Test
    void getUserHobbies_should_return_levels_when_sports_are_found_in_database() {
        final Integer ID1 = 1;
        final Integer ID2 = 2;
        final Integer ID3 = 3;

        when(hobbiesRepository.findUserHobbies(ID))
                .thenReturn(asList(
                        new Hobbies(ID1, RUNNING.getDatabaseValue()),
                        new Hobbies(ID2, SWIMMING.getDatabaseValue()),
                        new Hobbies(ID3, BIKE.getDatabaseValue()))
                );

        assertThat(hobbiesService.getUserHobbies(ID))
                .isEqualTo(asList(RUNNING.getDatabaseValue(), SWIMMING.getDatabaseValue(), BIKE.getDatabaseValue()));
    }
}