package com.example.sportmate.repository;

import com.example.sportmate.entity.UserFavorieSportId;
import com.example.sportmate.entity.UserFavoriteSport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteSportRepository extends CrudRepository<UserFavoriteSport, UserFavorieSportId> {
    @Modifying
    @Query(value = "INSERT INTO user_favorite_sport VALUES (:userId, :sportId, :levelId)", nativeQuery = true)
    void save(@Param("userId") int userId, @Param("sportId") int sportId, @Param("levelId") int levelId);

    @Query(value = "SELECT * FROM user_favorite_sport ufs " +
            "INNER JOIN sport s ON s.id = ufs.sport_id " +
            "INNER JOIN users u ON ufs.user_id = u.id AND u.id = :userId", nativeQuery = true)
    List<UserFavoriteSport> findUserSports(Integer userId);

    @Modifying
    @Query(value = "DELETE FROM user_favorite_sport WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(Integer userId);
}