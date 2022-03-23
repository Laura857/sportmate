package com.example.sportmate.service;

import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.repository.SportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.sportmate.enumeration.LevelEnum.*;
import static com.example.sportmate.enumeration.SportEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class SportServiceTest {

    @Autowired
    SportService sportService;

    @MockBean
    SportRepository sportRepository;

    @Test
    void getAllSports_should_return_emptyList_when_no_sports_are_found_in_database() {
        when(sportRepository.findAll())
                .thenReturn(emptyList());

        assertThat(sportService.getAllSports())
                .isEmpty();
    }

    @Test
    void getAllSports_should_return_levels_when_sports_are_found_in_database() {
        final Integer ID1 = 1;
        final Integer ID2 = 2;
        final Integer ID3 = 3;

        when(sportRepository.findAll())
                .thenReturn(asList(
                        new Sport(ID1, RUNNING.getDatabaseValue()),
                        new Sport(ID2, SWIMMING.getDatabaseValue()),
                        new Sport(ID3, BIKE.getDatabaseValue()))
                );

        assertThat(sportService.getAllSports())
                .isEqualTo(asList(RUNNING.getDatabaseValue(), SWIMMING.getDatabaseValue(), BIKE.getDatabaseValue()));
    }
}