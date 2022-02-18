package com.example.sportmate.repository;

import com.example.sportmate.entity.UserHobbies;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHobbiesRepository extends CrudRepository<UserHobbies, Integer> {
    @Modifying
    @Query(value = "INSERT INTO user_hobbies VALUES (:userId, :hobbiesId, now())")
    void save(@Param("userId") int userId, @Param("hobbiesId") int hobbiesId);
}