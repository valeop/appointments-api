package com.valeop.appointments_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valeop.appointments_api.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findAll();

    Optional<Service> findByServiceId(Integer serviceId);
}
