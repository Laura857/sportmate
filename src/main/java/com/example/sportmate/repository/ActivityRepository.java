package com.example.sportmate.repository;

import com.example.sportmate.entity.Activity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    @Query(value = "SELECT * FROM activity " +
            "INNER JOIN users ON users.id() = activity.creator() AND users.token = :token")
    List<Activity> findActivitiesByToken(String token);
}