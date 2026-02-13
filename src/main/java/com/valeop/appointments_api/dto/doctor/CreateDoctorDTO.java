package com.valeop.appointments_api.dto.doctor;

import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.User;

import jakarta.validation.constraints.NotNull;

public record CreateDoctorDTO(
        @NotNull(message = "User should not be empty.") User user,
        @NotNull(message = "Person should not be empty.") Person person) {
}
