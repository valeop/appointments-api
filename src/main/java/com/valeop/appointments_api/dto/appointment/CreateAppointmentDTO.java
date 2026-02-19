package com.valeop.appointments_api.dto.appointment;

import java.time.LocalDateTime;

import com.valeop.appointments_api.model.DoctorService;
import com.valeop.appointments_api.model.User;

import jakarta.validation.constraints.NotNull;

public record CreateAppointmentDTO(
        @NotNull(message = "appointmentDateTime should not be empty") LocalDateTime appointmentDateTime,
        @NotNull(message = "User shold not be empty") User user,
        @NotNull(message = "doctorService should not be empty") DoctorService doctorService) {
}
