package com.example.sportmate.repository;

import com.example.sportmate.entity.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SportRepositoryTest {
    private final String SPORT_NAME = "Natation";

    private final SportRepository sportRepository;

    @Autowired
    SportRepositoryTest(final SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    @Test
    void findById_should_find_a_new_saved_sport() {
        int sportSavedId = instantiateAndSaveNewSport();
        Optional<Sport> sportFind = sportRepository.findById(sportSavedId);
        assertThat(sportFind).isPresent();
        assertThat(sportFind.get()).isEqualTo(new Sport(sportSavedId, SPORT_NAME));
    }

    @Test
    void findById_should_not_find_an_unsaved_sport() {
        Optional<Sport> sportFind = sportRepository.findById(-1);
        assertThat(sportFind).isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_sport() {
        int sportSavedId = instantiateAndSaveNewSport();
        sportRepository.deleteById(sportSavedId);
        Optional<Sport> sportFind = sportRepository.findById(sportSavedId);
        assertThat(sportFind).isEmpty();
    }

    private int instantiateAndSaveNewSport() {
        final Sport sport = new Sport(null, SPORT_NAME);
        return sportRepository.save(sport).id();
    }
}