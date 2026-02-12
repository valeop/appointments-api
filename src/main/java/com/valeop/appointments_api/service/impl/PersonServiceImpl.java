package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.person.CreatePersonDTO;
import com.valeop.appointments_api.dto.person.PersonResponseDTO;
import com.valeop.appointments_api.dto.person.UpdatePersonDTO;
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
        public List<PersonResponseDTO> getPersonList() {
                List<Person> personList = personRepository.findAll().stream()
                                .toList();

                return personList.stream().map(PersonMapper::toResponseDTO).toList();
        }

        @Override
        public PersonResponseDTO getPersonById(Integer personId) {
                Person personFound = personRepository.findById(personId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + personId));

                return PersonMapper.toResponseDTO(personFound);
        }

        @Override
        public PersonResponseDTO getPersonByIdentityCard(String identityCard) {
                Person personFound = personRepository.findByIdentityCard(identityCard)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + identityCard));
                return PersonMapper.toResponseDTO(personFound);
        }

        @Override
        public PersonResponseDTO createPerson(CreatePersonDTO personDTO) {
                Gender genderFound = genderRepository.findByGenderId(personDTO.gender().getGenderId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Gender does not exist."));

                BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(personDTO.bloodType().getBloodTypeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BloodType does not exist."));

                Person newPerson = PersonMapper.fromCreatePersonDTO(personDTO, genderFound, bloodTypeFound);

                return PersonMapper.toResponseDTO(personRepository.save(newPerson));
        }

        @Override
        public PersonResponseDTO updatePerson(UpdatePersonDTO personDTO, Integer personId) {
                Person personFound = personRepository.findByPersonId(personId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Person with ID #" + personId + MESSAGE));

                if (personDTO.gender() != null) {
                        Gender genderFound = genderRepository.findByGenderId(personDTO.gender().getGenderId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Gender does not exist."));
                        personFound.setGender(genderFound);
                }

                if (personDTO.bloodType() != null) {
                        BloodType bloodTypeFound = bloodTypeRepository
                                        .findByBloodTypeId(personDTO.bloodType().getBloodTypeId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Blood Type does not exist."));
                        personFound.setBloodType(bloodTypeFound);
                }

                PersonMapper.updateFromDTO(personDTO, personFound);

                Person personSaved = personRepository.save(personFound);
                return PersonMapper.toResponseDTO(personSaved);
        }

        @Override
        public PersonResponseDTO deletePerson(Integer personId) {
                Person personFound = personRepository.findByPersonId(personId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + personId));

                personRepository.delete(personFound);
                return PersonMapper.toResponseDTO(personFound);
        }

}
