package com.example.sportmate.repository;

import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelRepository extends CrudRepository<Level, Integer> {
    Optional<Level> findByLabel(String label);
}