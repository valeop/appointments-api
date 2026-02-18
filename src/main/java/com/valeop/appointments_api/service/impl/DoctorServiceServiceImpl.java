package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.doctorservice.CreateDoctorServiceDTO;
import com.valeop.appointments_api.dto.doctorservice.DoctorServiceResponseDTO;
import com.valeop.appointments_api.dto.doctorservice.UpdateDoctorServiceDTO;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.DoctorServiceMapper;
import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.DoctorService;
import com.valeop.appointments_api.repository.DoctorRepository;
import com.valeop.appointments_api.repository.DoctorServiceRepository;
import com.valeop.appointments_api.repository.ServiceRepository;
import com.valeop.appointments_api.service.DoctorServiceService;

@Service
public class DoctorServiceServiceImpl implements DoctorServiceService {
    private final DoctorServiceRepository doctorServiceRepository;
    private final DoctorRepository doctorRepository;
    private final ServiceRepository serviceRepository;
    private static final String MESSAGE = "doctorService does not exist with ID #";

    public DoctorServiceServiceImpl(DoctorServiceRepository doctorServiceRepository, DoctorRepository doctorRepository,
            ServiceRepository serviceRepository) {
        this.doctorServiceRepository = doctorServiceRepository;
        this.doctorRepository = doctorRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<DoctorServiceResponseDTO> getDoctorServicesList() {
        return doctorServiceRepository.findAll()
                .stream().map(DoctorServiceMapper::toResponseDTO).toList();
    }

    @Override
    public DoctorServiceResponseDTO getDoctorServiceById(Integer doctorServiceId) {
        DoctorService doctorServiceFound = doctorServiceRepository.findByDoctorServiceId(doctorServiceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + doctorServiceId));
        return DoctorServiceMapper.toResponseDTO(doctorServiceFound);
    }

    @Override
    public DoctorServiceResponseDTO createDoctorService(CreateDoctorServiceDTO doctorServiceDTO) {
        Doctor doctorFound = doctorRepository.findByDoctorId(doctorServiceDTO.doctor().getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor does not exist."));

        com.valeop.appointments_api.model.Service serviceFound = serviceRepository
                .findByServiceId(doctorServiceDTO.service().getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service does not exist."));

        DoctorService doctorService = DoctorServiceMapper.fromCreateDoctorService(doctorFound, serviceFound);
        doctorServiceRepository.save(doctorService);
        return DoctorServiceMapper.toResponseDTO(doctorService);
    }

    @Override
    public DoctorServiceResponseDTO updateDoctorService(UpdateDoctorServiceDTO doctorServiceDTO,
            Integer doctorServiceId) {

        DoctorService doctorServiceFound = doctorServiceRepository.findByDoctorServiceId(doctorServiceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + doctorServiceId));

        if (doctorServiceDTO.doctor() != null) {
            Integer doctorId = doctorServiceDTO.doctor().getDoctorId();
            Doctor doctorFound = doctorRepository.findByDoctorId(doctorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor does not exist with ID #" + doctorId));

            doctorServiceFound.setDoctor(doctorFound);
        }
        if (doctorServiceDTO.service() != null) {
            Integer serviceId = doctorServiceDTO.service().getServiceId();
            com.valeop.appointments_api.model.Service serviceFound = serviceRepository.findByServiceId(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Service does not exist with ID #" + serviceId));

            doctorServiceFound.setService(serviceFound);
        }
        doctorServiceRepository.save(doctorServiceFound);
        return DoctorServiceMapper.toResponseDTO(doctorServiceFound);
    }

    @Override
    public DoctorServiceResponseDTO deleteDoctorService(Integer doctorServiceId) {
        DoctorService doctorServiceFound = doctorServiceRepository.findByDoctorServiceId(doctorServiceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + doctorServiceId));
        doctorServiceRepository.deleteById(doctorServiceFound.getDoctorServiceId());
        return DoctorServiceMapper.toResponseDTO(doctorServiceFound);
    }
}