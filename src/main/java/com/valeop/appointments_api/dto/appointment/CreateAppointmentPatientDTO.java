package com.valeop.appointments_api.dto.appointment;

import java.time.LocalDateTime;

import com.valeop.appointments_api.model.DoctorService;

import jakarta.validation.constraints.NotNull;

public record CreateAppointmentPatientDTO(
        @NotNull(message = "appointmentDateTime should not be empty") LocalDateTime appointmentDateTime,
        @NotNull(message = "doctorService should not be empty") DoctorService doctorService) {
}
