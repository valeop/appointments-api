package com.valeop.appointments_api.dto.gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGenderDTO(
        @NotBlank(message = "Gender name should not be empty.") @Size(max = 20, min = 2, message = "Gender name characters limit exceeded. (20)") String genderName) {
}