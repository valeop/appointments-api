package com.valeop.appointments_api.dto.appointment;

import java.time.LocalDateTime;

import com.valeop.appointments_api.model.DoctorService;
import com.valeop.appointments_api.model.User;

public record AppointmentResponseDTO(
        Integer appointmentId,
        LocalDateTime appointmentDateTime,
        User user,
        DoctorService doctorService) {
}
