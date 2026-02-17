package com.valeop.appointments_api.dto.service;

import com.valeop.appointments_api.model.ServiceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateServiceDTO(
        @NotBlank(message = "serviceName should not be empty") String serviceName,
        @NotNull(message = "serviceType should not be empty") ServiceType serviceType) {
}