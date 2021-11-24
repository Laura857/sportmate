package com.example.sportmate.repository;

import com.example.sportmate.entity.UserActivity;
import com.example.sportmate.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends CrudRepository<UserActivity, Integer> {
}