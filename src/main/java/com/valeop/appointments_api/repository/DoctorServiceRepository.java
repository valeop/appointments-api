package com.valeop.appointments_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.DoctorService;

@Repository
public interface DoctorServiceRepository extends JpaRepository<DoctorService, Integer> {
    List<DoctorService> findAll();

    Optional<DoctorService> findByDoctorServiceId(Integer doctorServiceId);

    Optional<DoctorService> findByDoctor(Doctor doctor);
}