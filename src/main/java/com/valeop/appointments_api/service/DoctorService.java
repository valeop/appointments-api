package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.doctor.CreateDoctorDTO;
import com.valeop.appointments_api.dto.doctor.DoctorResponseDTO;
import com.valeop.appointments_api.dto.doctor.UpdateDoctorDTO;

@Service
public interface DoctorService {
    List<DoctorResponseDTO> getDoctorsList();

    DoctorResponseDTO getDoctorById(Integer doctorId);

    DoctorResponseDTO createDoctor(CreateDoctorDTO createDoctorDTO);

    DoctorResponseDTO updateDoctor(UpdateDoctorDTO updateDoctorDTO, Integer doctorId);

    DoctorResponseDTO deleteDoctor(Integer doctorId);
}
