package com.example.sportmate.repository;

import com.example.sportmate.entity.UserHobbies;
import com.example.sportmate.entity.UserHobbiesId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHobbiesRepository extends CrudRepository<UserHobbies, UserHobbiesId> {
    @Modifying
    @Query(value = "INSERT INTO user_hobbies VALUES (:userId, :hobbiesId)", nativeQuery = true)
    void save(@Param("userId") int userId, @Param("hobbiesId") int hobbiesId);

    @Modifying
    @Query(value = "DELETE FROM user_hobbies WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(@Param("userId") Integer userId);
}