package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.PersonDTO;

@Service
public interface PersonService {
    List<PersonDTO> getPersonList();

    PersonDTO getPersonById(Integer personId);

    PersonDTO getPersonByIdentityCard(String identityCard);

    PersonDTO createPerson(PersonDTO personDTO, Integer genderId, Integer bloodTypeId);

    PersonDTO updatePerson(PersonDTO personDTO, Integer personId);

    PersonDTO deletePerson(Integer personId);
}
