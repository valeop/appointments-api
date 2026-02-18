package com.valeop.appointments_api.dto.doctorservice;

import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.Service;

public record UpdateDoctorServiceDTO(
        Integer doctorServiceId,
        Doctor doctor,
        Service service) {
}
