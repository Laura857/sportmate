package com.example.sportmate.repository;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Level;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LevelEnumRepositoryTest implements DataTest {

    private final LevelRepository levelRepository;

    @Autowired
    LevelEnumRepositoryTest(final LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Test
    void findById_should_find_a_new_saved_level() {
        final int levelSavedId = instantiateAndSaveNewLevel();
        final Optional<Level> levelFind = levelRepository.findById(levelSavedId);
        assertThat(levelFind).isPresent();
        assertThat(levelFind.get()).isEqualTo(new Level(levelSavedId, LEVEL_NAME));
    }

    @Test
    void findById_should_not_find_an_unsaved_level() {
        final Optional<Level> levelFind = levelRepository.findById(-1);
        assertThat(levelFind).isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_level() {
        final int levelSavedId = instantiateAndSaveNewLevel();
        levelRepository.deleteById(levelSavedId);
        final Optional<Level> levelFind = levelRepository.findById(levelSavedId);
        assertThat(levelFind).isEmpty();
    }

    private int instantiateAndSaveNewLevel() {
        final Level level = new Level(null, LEVEL_NAME);
        return levelRepository.save(level).id();
    }
}