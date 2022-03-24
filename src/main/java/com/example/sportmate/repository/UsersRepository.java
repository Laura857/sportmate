package com.example.sportmate.repository;

import com.example.sportmate.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndIdNot(String email, Integer id);
}