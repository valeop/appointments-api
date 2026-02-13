package com.valeop.appointments_api.dto.doctor;

import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.User;

public record DoctorResponseDTO(
        Integer doctorId,
        User user,
        Person person) {
}
