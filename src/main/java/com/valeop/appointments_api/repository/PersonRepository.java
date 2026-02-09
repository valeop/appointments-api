package com.valeop.appointments_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valeop.appointments_api.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findAll();

    Optional<Person> findByPersonId(Integer personId);

    Optional<Person> findByIdentityCard(String identityCard);
}