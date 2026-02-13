package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.doctor.DoctorResponseDTO;
import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.User;

@Component
public class DoctorMapper {

    private DoctorMapper() {
    }

    public static Doctor fromCreateDoctorDTO(User user, Person person) {
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setPerson(person);
        return doctor;
    }

    public static DoctorResponseDTO toResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(doctor.getDoctorId(), doctor.getUser(), doctor.getPerson());
    }
}
