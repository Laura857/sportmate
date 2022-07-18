package com.example.sportmate.repository;

import com.example.sportmate.entity.Sport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportRepository extends CrudRepository<Sport, Integer> {
    Optional<Sport> findByLabel(String label);
}