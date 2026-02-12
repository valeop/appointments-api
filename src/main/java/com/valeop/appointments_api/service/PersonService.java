package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.person.CreatePersonDTO;
import com.valeop.appointments_api.dto.person.PersonResponseDTO;
import com.valeop.appointments_api.dto.person.UpdatePersonDTO;

@Service
public interface PersonService {
    List<PersonResponseDTO> getPersonList();

    PersonResponseDTO getPersonById(Integer personId);

    PersonResponseDTO getPersonByIdentityCard(String identityCard);

    PersonResponseDTO createPerson(CreatePersonDTO personDTO);

    PersonResponseDTO updatePerson(UpdatePersonDTO personDTO, Integer personId);

    PersonResponseDTO deletePerson(Integer personId);
}