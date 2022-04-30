package com.example.sportmate.repository.activity;

import com.example.sportmate.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {

    @Query(value = "SELECT * FROM activity " +
            "INNER JOIN users ON activity.creator = users.id AND users.email = :email", nativeQuery = true)
    List<Activity> findActivitiesByEmail(String email);

    @Query(value = "SELECT * FROM activity " +
            "INNER JOIN user_activity ON user_activity.activity_id = activity.id " +
            " WHERE user_id = :userId", nativeQuery = true)
    List<Activity> findParticipateActivitiesByUserId(Integer userId);

    @Modifying
    @Query(value = "DELETE FROM activity WHERE creator = :userId", nativeQuery = true)
    void deleteAllByCreator(Integer userId);
}