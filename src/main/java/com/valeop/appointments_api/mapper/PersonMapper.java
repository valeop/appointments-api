package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.person.CreatePersonDTO;
import com.valeop.appointments_api.dto.person.PersonResponseDTO;
import com.valeop.appointments_api.dto.person.UpdatePersonDTO;
import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.model.Gender;
import com.valeop.appointments_api.model.Person;

@Component
public class PersonMapper {

    private PersonMapper() {
    }

    public static Person fromCreatePersonDTO(CreatePersonDTO dto, Gender gender, BloodType bloodType) {
        Person person = new Person();
        person.setGender(gender);
        person.setBloodType(bloodType);
        person.setIdentityCard(dto.identityCard());
        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());
        person.setBirthDate(dto.birthDate());
        return person;
    }

    public static void updateFromDTO(UpdatePersonDTO dto, Person person) {
        if (!dto.identityCard().isBlank()) {
            person.setIdentityCard(dto.identityCard());
        }
        if (!dto.firstName().isBlank()) {
            person.setFirstName(dto.firstName());
        }
        if (!dto.lastName().isBlank()) {
            person.setLastName(dto.lastName());
        }
        if (dto.birthDate() != null) {
            person.setBirthDate(dto.birthDate());
        }
    }

    public static PersonResponseDTO toResponseDTO(Person person) {
        return new PersonResponseDTO(person.getPersonId(), person.getGender(), person.getBloodType(),
                person.getIdentityCard(), person.getFirstName(), person.getLastName(), person.getBirthDate());
    }
}
