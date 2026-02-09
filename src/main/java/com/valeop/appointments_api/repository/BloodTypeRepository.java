package com.valeop.appointments_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valeop.appointments_api.model.BloodType;

public interface BloodTypeRepository extends JpaRepository<BloodType, Integer> {

    List<BloodType> findAll();
    
    Optional<BloodType> findByBloodTypeId(Integer bloodTypeId);
}
