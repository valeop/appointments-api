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

    public static Appointment createFromDTO(CreateAppointmentDTO dto, User user,
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
        String patientFullName = appointment.getUser().getPerson().getFirstName() + " "
                + appointment.getUser().getPerson().getLastName();
        String doctorFullName = appointment.getDoctorService().getDoctor().getPerson().getFirstName()
                + " " + appointment.getDoctorService().getDoctor().getPerson().getLastName();

        return new AppointmentResponseDTO(appointment.getAppointmentId(), appointment.getAppointmentDateTime(),
                patientFullName, doctorFullName,
                appointment.getDoctorService().getService().getServiceName(),
                appointment.getDoctorService().getService().getServiceType().getServiceTypeName());
    }
}