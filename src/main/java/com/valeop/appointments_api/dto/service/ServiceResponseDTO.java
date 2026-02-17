package com.valeop.appointments_api.dto.service;

import com.valeop.appointments_api.model.ServiceType;

public record ServiceResponseDTO(
        Integer serviceId,
        String serviceName,
        ServiceType serviceType) {
}
