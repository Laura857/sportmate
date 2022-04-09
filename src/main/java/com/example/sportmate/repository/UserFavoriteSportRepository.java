package com.example.sportmate.repository;

import com.example.sportmate.entity.UserFavoriteSport;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteSportRepository extends CrudRepository<UserFavoriteSport, Integer> {
    @Modifying
    @Query(value = "INSERT INTO user_favorite_sport VALUES (:userId, :sportId, :levelId)")
    void save(@Param("userId") int userId, @Param("sportId") int sportId, @Param("levelId") int levelId);

    @Query("SELECT * FROM user_favorite_sport ufs " +
            "INNER JOIN sport s ON s.id = ufs.fk_id_sport " +
            "INNER JOIN users u ON ufs.fk_id_user = u.id AND u.id = :userId")
    List<UserFavoriteSport> findUserSports(Integer userId);
}