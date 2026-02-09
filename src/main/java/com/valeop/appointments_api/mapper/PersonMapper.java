package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.PersonDTO;
import com.valeop.appointments_api.model.Person;

@Component
public class PersonMapper {

    private PersonMapper() {
    }

    public static PersonDTO toDTO(Person person) {
        return new PersonDTO(person.getPersonId(), person.getGender(), person.getBloodType(),
                person.getIdentityCard(), person.getFirstName(), person.getLastName(),
                person.getBirthDate());
    }

    public static Person toEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setPersonId(personDTO.getPersonId());
        person.setGender(personDTO.getGender());
        person.setBloodType(personDTO.getBloodType());
        person.setIdentityCard(personDTO.getIdentityCard());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setBirthDate(personDTO.getBirthDate());
        return person;
    }
}
