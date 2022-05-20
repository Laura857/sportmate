package com.example.sportmate.repository;

import com.example.sportmate.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndIdNot(String email, Integer id);

    @Query(value = "SELECT * FROM users " +
            "INNER JOIN user_activity ua ON users.id = ua.user_id " +
            "WHERE activity_id = :activityId", nativeQuery = true)
    List<Users> findActivityParticipants(Integer activityId);
}