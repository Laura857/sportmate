package com.example.sportmate.repository;

import com.example.sportmate.entity.UserActivity;
import com.example.sportmate.entity.UserActivityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, UserActivityId> {
    @Modifying
    @Query(value = "DELETE FROM user_activity WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(Integer userId);

    @Modifying
    @Query(value = "DELETE FROM user_activity WHERE user_id = :userId AND activity_id = :activityId", nativeQuery = true)
    void deleteByUserIdAndActivityId(Integer userId, Integer activityId);

    @Modifying
    @Query(value = "INSERT INTO user_activity VALUES (:userId, :activityId)", nativeQuery = true)
    void save(int userId, int activityId);

}