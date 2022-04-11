package com.example.sportmate.repository;

import com.example.sportmate.entity.Hobbies;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HobbiesRepository extends CrudRepository<Hobbies, Integer> {
    Optional<Hobbies> findByLabel(String label);

    @Query("SELECT * FROM hobbies " +
            "INNER JOIN user_hobbies uh ON hobbies.id = uh.hobbies_id " +
            "INNER JOIN users u ON uh.user_id = u.id AND u.id = :userId")
    List<Hobbies> findUserHobbies(Integer userId);

}