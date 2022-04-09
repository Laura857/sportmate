package com.example.sportmate.repository;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SportRepositoryTest implements DataTest {

    private final SportRepository sportRepository;

    @Autowired
    SportRepositoryTest(final SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    @Test
    void findById_should_find_a_new_saved_sport() {
        final int sportSavedId = instantiateAndSaveNewSport();
        final Optional<Sport> sportFind = sportRepository.findById(sportSavedId);
        assertThat(sportFind).isPresent();
        assertThat(sportFind.get()).isEqualTo(new Sport(sportSavedId, SPORT_NAME_SWIM));
    }

    @Test
    void findById_should_not_find_an_unsaved_sport() {
        final Optional<Sport> sportFind = sportRepository.findById(-1);
        assertThat(sportFind).isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_sport() {
        final int sportSavedId = instantiateAndSaveNewSport();
        sportRepository.deleteById(sportSavedId);
        final Optional<Sport> sportFind = sportRepository.findById(sportSavedId);
        assertThat(sportFind).isEmpty();
    }

    private int instantiateAndSaveNewSport() {
        final Sport sport = new Sport(null, SPORT_NAME_SWIM);
        return sportRepository.save(sport).id();
    }
}