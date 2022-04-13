package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.entity.UserHobbies;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.hobbies.UpdateUserHobbiesDto;
import com.example.sportmate.repository.HobbiesRepository;
import com.example.sportmate.repository.UserHobbiesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.example.sportmate.enumeration.ErrorMessageEnum.HOBBIES_NOT_FOUND;
import static com.example.sportmate.enumeration.SportEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@SpringBootTest
class HobbiesServiceTest implements DataTest {
    @Autowired
    HobbiesService hobbiesService;

    @MockBean
    HobbiesRepository hobbiesRepository;

    @MockBean
    UserHobbiesRepository userHobbiesRepository;

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

    @Test
    void updateUserHobbies_should_throw_exception_when_hobbies_is_not_found() {
        when(hobbiesRepository.findByLabel(HOBBIES_MOVIES))
                .thenReturn(empty());

        verify(userHobbiesRepository, never()).saveAll(anyCollection());

        final UpdateUserHobbiesDto updateUserHobbiesDto = new UpdateUserHobbiesDto(singletonList(HOBBIES_MOVIES));
        assertThatThrownBy(() -> hobbiesService.updateUserHobbies(ID, updateUserHobbiesDto))
                .hasMessage(HOBBIES_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateUserHobbies_should_save_hobbies_when_hobbies_are_found() {
        when(hobbiesRepository.findByLabel(HOBBIES_MOVIES))
                .thenReturn(Optional.of(new Hobbies(ID, HOBBIES_MOVIES)));

        when(hobbiesRepository.findByLabel(HOBBIES_BOOK))
                .thenReturn(Optional.of(new Hobbies(ID_2, HOBBIES_BOOK)));

        final List<UserHobbies> userHobbies = asList(new UserHobbies(ID, ID),
                new UserHobbies(ID, ID_2));

        when(userHobbiesRepository.saveAll(userHobbies))
                .thenReturn(userHobbies);

        assertDoesNotThrow(() -> hobbiesService.updateUserHobbies(ID, new UpdateUserHobbiesDto(asList(HOBBIES_MOVIES, HOBBIES_BOOK))));
    }
}