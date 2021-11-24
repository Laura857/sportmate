package com.example.sportmate.repository;

import com.example.sportmate.entity.Level;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LevelRepositoryTest {
    private final String LEVEL_NAME = "Débutant";

    private final LevelRepository levelRepository;

    @Autowired
    LevelRepositoryTest(final LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Test
    void findById_should_find_a_new_saved_level() {
        int levelSavedId = instantiateAndSaveNewLevel();
        Optional<Level> levelFind = levelRepository.findById(levelSavedId);
        assertThat(levelFind).isPresent();
        assertThat(levelFind.get()).isEqualTo(new Level(levelSavedId, LEVEL_NAME));
    }

    @Test
    void findById_should_not_find_an_unsaved_level() {
        Optional<Level> levelFind = levelRepository.findById(-1);
        assertThat(levelFind).isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_level() {
        int levelSavedId = instantiateAndSaveNewLevel();
        levelRepository.deleteById(levelSavedId);
        Optional<Level> levelFind = levelRepository.findById(levelSavedId);
        assertThat(levelFind).isEmpty();
    }

    private int instantiateAndSaveNewLevel() {
        final Level level = new Level(null, LEVEL_NAME);
        return levelRepository.save(level).id();
    }
}