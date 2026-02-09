package com.valeop.appointments_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valeop.appointments_api.model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    List<Gender> findAll();
    
    Optional<Gender> findByGenderId(Integer genderId);
}
