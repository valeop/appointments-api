package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.doctorservice.CreateDoctorServiceDTO;
import com.valeop.appointments_api.dto.doctorservice.DoctorServiceResponseDTO;
import com.valeop.appointments_api.dto.doctorservice.UpdateDoctorServiceDTO;

@Service
public interface DoctorServiceService {
    List<DoctorServiceResponseDTO> getDoctorServicesList();

    DoctorServiceResponseDTO getDoctorServiceById(Integer doctorServiceId);

    DoctorServiceResponseDTO createDoctorService(CreateDoctorServiceDTO doctorServiceDTO);

    DoctorServiceResponseDTO updateDoctorService(UpdateDoctorServiceDTO doctorServiceDTO, Integer doctorServiceId);

    DoctorServiceResponseDTO deleteDoctorService(Integer doctorServiceId);
}
