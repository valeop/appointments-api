package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.servicetype.CreateServiceTypeDTO;
import com.valeop.appointments_api.dto.servicetype.ServiceTypeResponseDTO;
import com.valeop.appointments_api.dto.servicetype.UpdateServiceTypeDTO;
import com.valeop.appointments_api.model.ServiceType;

@Component
public class ServiceTypeMapper {
    private ServiceTypeMapper() {
    }

    public static ServiceType fromCreateServiceTypeDTO(CreateServiceTypeDTO dto) {
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceTypeName(dto.serviceTypeName());
        return serviceType;
    }

    public static void updateFromDTO(UpdateServiceTypeDTO dto, ServiceType serviceType) {
        if (!dto.serviceTypeName().isBlank()) {
            serviceType.setServiceTypeName(dto.serviceTypeName());
        }
    }

    public static ServiceTypeResponseDTO toResponseDTO(ServiceType serviceType) {
        return new ServiceTypeResponseDTO(serviceType.getServiceTypeId(), serviceType.getServiceTypeName());
    }
}
