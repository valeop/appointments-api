package com.valeop.appointments_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valeop.appointments_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    Optional<User> findByUserId(Integer userId);
}
