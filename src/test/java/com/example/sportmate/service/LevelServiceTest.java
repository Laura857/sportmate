package com.example.sportmate.service;

import com.example.sportmate.entity.Level;
import com.example.sportmate.repository.LevelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.sportmate.enumeration.LevelEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class LevelServiceTest {

    @Autowired
    LevelService levelService;

    @MockBean
    LevelRepository levelRepository;

    @Test
    void getAllLevels_should_return_emptyList_when_no_level_are_found_in_database() {
        when(levelRepository.findAll())
                .thenReturn(emptyList());

        assertThat(levelService.getAllLevels())
                .isEmpty();
    }

    @Test
    void getAllLevels_should_return_levels_when_levels_are_found_in_database() {
        final Integer ID1 = 1;
        final Integer ID2 = 2;
        final Integer ID3 = 3;

        when(levelRepository.findAll())
                .thenReturn(asList(
                        new Level(ID1, BEGINNER.getDatabaseValue()),
                        new Level(ID2, INTERMEDIATE.getDatabaseValue()),
                        new Level(ID3, EXPERT.getDatabaseValue()))
                );

        assertThat(levelService.getAllLevels())
                .isEqualTo(asList(BEGINNER.getDatabaseValue(), INTERMEDIATE.getDatabaseValue(), EXPERT.getDatabaseValue()));
    }
}