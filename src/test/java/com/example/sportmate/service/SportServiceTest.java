package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.UserFavorieSportId;
import com.example.sportmate.entity.UserFavoriteSport;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.authentification.signing.SportDto;
import com.example.sportmate.repository.LevelRepository;
import com.example.sportmate.repository.SportRepository;
import com.example.sportmate.repository.UserFavoriteSportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.sportmate.enumeration.ErrorMessageEnum.LEVEL_NOT_FOUND;
import static com.example.sportmate.enumeration.ErrorMessageEnum.SPORT_NOT_FOUND;
import static com.example.sportmate.enumeration.SportEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class SportServiceTest implements DataTest {

    @Autowired
    SportService sportService;

    @MockBean
    SportRepository sportRepository;

    @MockBean
    LevelRepository levelRepository;

    @MockBean
    UserFavoriteSportRepository userFavoriteSportRepository;

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

    @Test
    void getUserSports_should_return_emptyList_when_no_user_favorites_sports_are_found_in_database() {
        when(userFavoriteSportRepository.findUserSports(ID))
                .thenReturn(emptyList());

        assertThat(sportService.getUserSports(ID))
                .isEmpty();
    }

    @Test
    void getUserSports_should_throw_exception_when_user_favorites_sports_are_found_in_database_but_sport_is_not_found() {
        when(userFavoriteSportRepository.findUserSports(ID))
                .thenReturn(singletonList(new UserFavoriteSport(ID, new UserFavorieSportId(ID, ID))));

        when(sportRepository.findById(ID))
                .thenReturn(empty());

        assertThatThrownBy(() -> sportService.getUserSports(ID))
                .hasMessageContaining(SPORT_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getUserSports_should_throw_exception_when_user_favorites_sports_are_found_in_database_but_level_is_not_found() {
        when(userFavoriteSportRepository.findUserSports(ID))
                .thenReturn(singletonList(new UserFavoriteSport(ID, new UserFavorieSportId(ID, ID))));

        when(sportRepository.findById(ID))
                .thenReturn(of(new Sport(ID, SPORT_NAME_SWIM)));

        when(levelRepository.findById(ID))
                .thenReturn(empty());

        assertThatThrownBy(() -> sportService.getUserSports(ID))
                .hasMessageContaining(LEVEL_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getUserSports_should_return_user_favorites_sports_and_level_when_user_favorites_sports_are_found_in_database() {
        when(userFavoriteSportRepository.findUserSports(ID))
                .thenReturn(asList(new UserFavoriteSport(ID, new UserFavorieSportId(ID, ID)),
                        new UserFavoriteSport(ID_2, new UserFavorieSportId(ID, ID_2))));

        when(sportRepository.findById(ID))
                .thenReturn(of(new Sport(ID, SPORT_NAME_SWIM)));

        when(levelRepository.findById(ID))
                .thenReturn(of(new Level(ID, LEVEL_NAME_BEGINNING)));

        when(sportRepository.findById(ID_2))
                .thenReturn(of(new Sport(ID_2, SPORT_NAME_RUNNING)));

        when(levelRepository.findById(ID_2))
                .thenReturn(of(new Level(ID_2, LEVEL_NAME_EXPERT)));

        assertThat(sportService.getUserSports(ID))
                .isEqualTo(asList(new SportDto(SPORT_NAME_SWIM, LEVEL_NAME_BEGINNING),
                        new SportDto(SPORT_NAME_RUNNING, LEVEL_NAME_EXPERT)));
    }
}