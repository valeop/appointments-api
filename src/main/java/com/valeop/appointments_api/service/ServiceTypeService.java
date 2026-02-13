package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.servicetype.CreateServiceTypeDTO;
import com.valeop.appointments_api.dto.servicetype.ServiceTypeResponseDTO;
import com.valeop.appointments_api.dto.servicetype.UpdateServiceTypeDTO;

@Service
public interface ServiceTypeService {
    List<ServiceTypeResponseDTO> getServiceTypesList();

    ServiceTypeResponseDTO getServiceTypeById(Integer serviceTypeId);

    ServiceTypeResponseDTO createServiceType(CreateServiceTypeDTO serviceTypeDTO);

    ServiceTypeResponseDTO updateServiceType(UpdateServiceTypeDTO serviceTypeDTO, Integer serviceTypeId);

    ServiceTypeResponseDTO deleteServiceType(Integer serviceTypeId);
}
