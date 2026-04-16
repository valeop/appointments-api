package com.valeop.appointments_api.dto.appointment;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(
                Integer appointmentId,
                LocalDateTime appointmentDateTime,
                String patient,
                String doctor,
                String service,
                String serviceType) {
}
