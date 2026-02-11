package com.valeop.appointments_api.dto.person;

import java.time.LocalDate;

import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.model.Gender;

public record PersonResponseDTO(
        Integer personId,
        Gender gender,
        BloodType bloodType,
        String identityCard,
        String firstName,
        String lastName,
        LocalDate birthDate) {
}
