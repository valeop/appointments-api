package com.valeop.appointments_api.dto.bloodtype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBloodTypeDTO(
        @NotBlank(message = "BloodType name should not be empty.") @Size(max = 20, min = 2, message = "BloodType name characters limit exceeded. (20)") String bloodTypeName) {
}
