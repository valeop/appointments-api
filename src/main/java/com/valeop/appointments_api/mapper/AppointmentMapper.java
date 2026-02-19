package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.appointment.AppointmentResponseDTO;
import com.valeop.appointments_api.dto.appointment.CreateAppointmentDTO;
import com.valeop.appointments_api.dto.appointment.UpdateAppointmentDTO;
import com.valeop.appointments_api.model.Appointment;
import com.valeop.appointments_api.model.DoctorService;
import com.valeop.appointments_api.model.User;

@Component
public class AppointmentMapper {
    private AppointmentMapper() {
    }

    public static Appointment fromCreateAppointmentDTO(CreateAppointmentDTO dto, User user,
            DoctorService doctorService) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(dto.appointmentDateTime());
        appointment.setUser(user);
        appointment.setDoctorService(doctorService);
        return appointment;
    }

    public static void updateFromDTO(UpdateAppointmentDTO dto, Appointment appointment) {
        if (dto.appointmentDateTime() != null) {
            appointment.setAppointmentDateTime(dto.appointmentDateTime());
        }
    }

    public static AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(appointment.getAppointmentId(), appointment.getAppointmentDateTime(),
                appointment.getUser(), appointment.getDoctorService());
    }
}