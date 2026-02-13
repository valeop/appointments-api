package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.doctor.CreateDoctorDTO;
import com.valeop.appointments_api.dto.doctor.DoctorResponseDTO;
import com.valeop.appointments_api.dto.doctor.UpdateDoctorDTO;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.DoctorMapper;
import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.User;
import com.valeop.appointments_api.repository.DoctorRepository;
import com.valeop.appointments_api.repository.PersonRepository;
import com.valeop.appointments_api.repository.UserRepository;
import com.valeop.appointments_api.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, UserRepository userRepository,
            PersonRepository personRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<DoctorResponseDTO> getDoctorsList() {
        List<Doctor> doctorList = doctorRepository.findAll();
        return doctorList.stream().map(DoctorMapper::toResponseDTO).toList();
    }

    @Override
    public DoctorResponseDTO getDoctorById(Integer doctorId) {
        Doctor doctorFound = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor was not found with ID #" + doctorId));
        return DoctorMapper.toResponseDTO(doctorFound);
    }

    @Override
    public DoctorResponseDTO createDoctor(CreateDoctorDTO createDoctorDTO) {
        User userFound = userRepository.findByUserId(createDoctorDTO.user().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist."));

        Person personFound = personRepository.findByPersonId(createDoctorDTO.person().getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person does not exist."));

        Doctor newDoctor = DoctorMapper.fromCreateDoctorDTO(userFound, personFound);
        doctorRepository.save(newDoctor);
        return DoctorMapper.toResponseDTO(newDoctor);
    }

    @Override
    public DoctorResponseDTO updateDoctor(UpdateDoctorDTO updateDoctorDTO, Integer doctorId) {
        Doctor doctorFound = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor does not exist with ID #" + doctorId));

        if (updateDoctorDTO.user() != null) {
            Integer userId = updateDoctorDTO.user().getUserId();
            if (!userRepository.existsById(userId)) {
                throw new ResourceNotFoundException("User does not exist with ID #" + userId);
            }
            doctorFound.setUser(userRepository.getReferenceById(userId));
        }
        if (updateDoctorDTO.person() != null) {
            Integer personId = updateDoctorDTO.person().getPersonId();
            if (!personRepository.existsById(personId)) {
                throw new ResourceNotFoundException("Person does not exist with ID #" + personId);
            }
            doctorFound.setPerson(personRepository.getReferenceById(personId));
        }
        Doctor doctorSaved = doctorRepository.save(doctorFound);
        return DoctorMapper.toResponseDTO(doctorSaved);
    }

    @Override
    public DoctorResponseDTO deleteDoctor(Integer doctorId) {
        Doctor doctorDeleted = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor does not exist with ID #" + doctorId));
        doctorRepository.deleteById(doctorId);
        return DoctorMapper.toResponseDTO(doctorDeleted);
    }
}
