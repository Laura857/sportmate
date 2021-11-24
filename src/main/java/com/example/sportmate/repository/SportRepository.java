package com.example.sportmate.repository;

import com.example.sportmate.entity.Sport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends CrudRepository<Sport, Integer> {
}