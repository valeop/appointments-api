package com.valeop.appointments_api.dto.person;

import java.time.LocalDate;

import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.model.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePersonDTO(
        @NotNull(message = "Gender should not be empty.") Gender gender,
        @NotNull(message = "Blood type should not be empty.") BloodType bloodType,
        @NotBlank(message = "Identity Card should not be empty.") @Size(max = 20, message = "Identity card characters limit exceeded. (20)") String identityCard,
        @NotBlank(message = "First name should not be empty.") @Size(max = 20, message = "First name characters limit exceeded. (20)") String firstName,
        @NotBlank(message = "Last name should not be empty.") @Size(max = 20, message = "Last name characters limit exceeded. (20)") String lastName,
        @NotNull(message = "Birthdate should not be empty.") LocalDate birthDate) {
}
