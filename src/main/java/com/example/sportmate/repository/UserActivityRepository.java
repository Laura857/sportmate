package com.example.sportmate.repository;

import com.example.sportmate.entity.UserActivity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends CrudRepository<UserActivity, Integer> {
    @Modifying
    @Query(value = "DELETE FROM user_activity WHERE user_id = :userId")
    void deleteAllByUserId(Integer userId);
}