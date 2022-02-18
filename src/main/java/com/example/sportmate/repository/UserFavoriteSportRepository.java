package com.example.sportmate.repository;

import com.example.sportmate.entity.UserFavoriteSport;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteSportRepository extends CrudRepository<UserFavoriteSport, Integer> {
    @Modifying
    @Query(value = "INSERT INTO user_favorite_sport VALUES (:userId, :sportId, :levelId)")
    void save(@Param("userId") int userId, @Param("sportId") int sportId, @Param("levelId") int levelId);
}