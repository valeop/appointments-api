package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.service.CreateServiceDTO;
import com.valeop.appointments_api.dto.service.ServiceResponseDTO;
import com.valeop.appointments_api.dto.service.UpdateServiceDTO;
import com.valeop.appointments_api.model.Service;
import com.valeop.appointments_api.model.ServiceType;

@Component
public class ServiceMapper {

    private ServiceMapper() {
    }

    public static Service fromCreateServiceDTO(CreateServiceDTO dto, ServiceType serviceFound) {
        Service service = new Service();
        service.setServiceName(dto.serviceName());
        service.setServiceType(serviceFound);
        return service;
    }

    public static void updateFromDTO(UpdateServiceDTO dto, Service service) {
        if (!dto.serviceName().isBlank()) {
            service.setServiceName(dto.serviceName());
        }
    }

    public static ServiceResponseDTO toResponseDTO(Service service) {
        return new ServiceResponseDTO(service.getServiceId(), service.getServiceName(), service.getServiceType());
    }
}
