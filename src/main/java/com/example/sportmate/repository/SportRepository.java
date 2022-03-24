package com.example.sportmate.repository;

import com.example.sportmate.entity.Sport;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportRepository extends CrudRepository<Sport, Integer> {
    Optional<Sport> findByLabel(String label);

    @Query("SELECT * FROM sport s " +
            "INNER JOIN user_favorite_sport ON s.id = user_favorite_sport.fk_id_sport " +
            "INNER JOIN users u ON user_favorite_sport.fk_id_user = u.id AND u.id = :userId")
    List<Sport> findUserSports(Integer userId);
}