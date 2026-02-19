package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.appointment.AppointmentResponseDTO;
import com.valeop.appointments_api.dto.appointment.CreateAppointmentDTO;
import com.valeop.appointments_api.dto.appointment.UpdateAppointmentDTO;

@Service
public interface AppointmentService {
    List<AppointmentResponseDTO> getAppointmentsList();

    AppointmentResponseDTO getAppointmentById(Integer appointmentId);

    AppointmentResponseDTO createAppointment(CreateAppointmentDTO createDTO);

    AppointmentResponseDTO updateAppointment(UpdateAppointmentDTO updateDTO, Integer appointmentId);

    AppointmentResponseDTO deleteAppointment(Integer appointmentId);
}
