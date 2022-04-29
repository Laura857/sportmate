package com.example.sportmate.repository;

import com.example.sportmate.entity.UserActivity;
import com.example.sportmate.entity.UserActivityId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends CrudRepository<UserActivity, UserActivityId> {
    @Modifying
    @Query(value = "DELETE FROM user_activity WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(Integer userId);
}