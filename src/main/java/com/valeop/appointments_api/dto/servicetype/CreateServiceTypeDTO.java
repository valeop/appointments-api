package com.valeop.appointments_api.dto.servicetype;

import jakarta.validation.constraints.NotBlank;

public record CreateServiceTypeDTO(
        @NotBlank(message = "ServiceType should not be empty") String serviceTypeName) {
}