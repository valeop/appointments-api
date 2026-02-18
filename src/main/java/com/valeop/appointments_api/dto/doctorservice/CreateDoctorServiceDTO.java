package com.valeop.appointments_api.dto.doctorservice;

import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.Service;

import jakarta.validation.constraints.NotNull;

public record CreateDoctorServiceDTO(
        @NotNull(message = "Doctor should not be empty") Doctor doctor,
        @NotNull(message = "Service should not be empty") Service service) {
}
