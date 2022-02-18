package com.example.sportmate.repository;

import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.entity.Sport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbiesRepository extends CrudRepository<Hobbies, Integer> {
    Optional<Hobbies> findByLabel(String label);
}