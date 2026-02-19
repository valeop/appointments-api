package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.servicetype.CreateServiceTypeDTO;
import com.valeop.appointments_api.dto.servicetype.ServiceTypeResponseDTO;
import com.valeop.appointments_api.model.ServiceType;

@Component
public class ServiceTypeMapper {
    private ServiceTypeMapper() {
    }

    public static ServiceType createFromDTO(CreateServiceTypeDTO dto) {
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceTypeName(dto.serviceTypeName());
        return serviceType;
    }

    public static ServiceTypeResponseDTO toResponseDTO(ServiceType serviceType) {
        return new ServiceTypeResponseDTO(serviceType.getServiceTypeId(), serviceType.getServiceTypeName());
    }
}
