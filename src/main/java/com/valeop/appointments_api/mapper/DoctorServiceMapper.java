package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.doctorservice.DoctorServiceResponseDTO;
import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.DoctorService;
import com.valeop.appointments_api.model.Service;

@Component
public class DoctorServiceMapper {

    private DoctorServiceMapper() {
    }

    public static DoctorService createFromDTO(Doctor doctor, Service service) {
        DoctorService doctorService = new DoctorService();
        doctorService.setDoctor(doctor);
        doctorService.setService(service);
        return doctorService;
    }

    public static DoctorServiceResponseDTO toResponseDTO(DoctorService doctorService) {
        return new DoctorServiceResponseDTO(doctorService.getDoctorServiceId(), doctorService.getDoctor(),
                doctorService.getService());
    }
}
