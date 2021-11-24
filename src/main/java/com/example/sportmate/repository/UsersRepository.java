package com.example.sportmate.repository;

import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
}