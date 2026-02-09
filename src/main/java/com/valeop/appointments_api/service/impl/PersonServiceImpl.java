package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.PersonDTO;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.PersonMapper;
import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.model.Gender;
import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.repository.BloodTypeRepository;
import com.valeop.appointments_api.repository.GenderRepository;
import com.valeop.appointments_api.repository.PersonRepository;
import com.valeop.appointments_api.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

        private final PersonRepository personRepository;
        private final GenderRepository genderRepository;
        private final BloodTypeRepository bloodTypeRepository;
        static final String MESSAGE = "Person does not exist with ID #";

        @Autowired
        public PersonServiceImpl(PersonRepository personRepository, GenderRepository genderRepository,
                        BloodTypeRepository bloodTypeRepository) {
                this.personRepository = personRepository;
                this.genderRepository = genderRepository;
                this.bloodTypeRepository = bloodTypeRepository;
        }

        @Override
        public List<PersonDTO> getPersonList() {
                List<Person> personList = personRepository.findAll().stream()
                                .toList();

                return personList.stream().map(PersonMapper::toDTO).toList();
        }

        @Override
        public PersonDTO getPersonById(Integer personId) {
                Person personFound = personRepository.findById(personId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + personId));

                return PersonMapper.toDTO(personFound);
        }

        @Override
        public PersonDTO getPersonByIdentityCard(String identityCard) {
                Person personFound = personRepository.findByIdentityCard(identityCard)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + identityCard));
                return PersonMapper.toDTO(personFound);
        }

        @Override
        public PersonDTO createPerson(PersonDTO personDTO, Integer genderId, Integer bloodTypeId) {
                Gender genderFound = genderRepository.findByGenderId(genderId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Gender does not exist with ID #" + genderId));

                BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(bloodTypeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BloodType does not exist with ID #" + bloodTypeId));

                Person newPerson = PersonMapper.toEntity(personDTO);

                newPerson.setGender(genderFound);
                newPerson.setBloodType(bloodTypeFound);

                Person personSaved = personRepository.save(newPerson);

                return PersonMapper.toDTO(personSaved);
        }

        @Override
        public PersonDTO updatePerson(PersonDTO personDTO, Integer personId) {
                Person personFound = personRepository.findByPersonId(personId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Person with ID #" + personId + MESSAGE));

                Gender genderFound = genderRepository.findByGenderId(personDTO.getGender().getGenderId())
                                .orElseThrow(() -> new ResourceNotFoundException("Gender does not exist."));

                BloodType bloodTypeFound = bloodTypeRepository
                                .findByBloodTypeId(personDTO.getBloodType().getBloodTypeId())
                                .orElseThrow(() -> new ResourceNotFoundException("Blood Type does not exist."));

                personFound.setIdentityCard(personDTO.getIdentityCard());
                personFound.setFirstName(personDTO.getFirstName());
                personFound.setLastName(personDTO.getLastName());
                personFound.setBirthDate(personDTO.getBirthDate());
                personFound.setGender(genderFound);
                personFound.setBloodType(bloodTypeFound);

                Person personSaved = personRepository.save(personFound);
                return PersonMapper.toDTO(personSaved);
        }

        @Override
        public PersonDTO deletePerson(Integer personId) {
                Person personFound = personRepository.findByPersonId(personId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + personId));

                personRepository.delete(personFound);
                return PersonMapper.toDTO(personFound);
        }

}
