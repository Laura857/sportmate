package com.example.sportmate.repository;

import com.example.sportmate.entity.Level;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends CrudRepository<Level, Integer> {
}