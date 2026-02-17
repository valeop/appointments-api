package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.service.CreateServiceDTO;
import com.valeop.appointments_api.dto.service.ServiceResponseDTO;
import com.valeop.appointments_api.dto.service.UpdateServiceDTO;

@Service
public interface ServiceService {
    List<ServiceResponseDTO> getServicesList();

    ServiceResponseDTO getServiceById(Integer serviceId);

    ServiceResponseDTO createService(CreateServiceDTO createDTO);

    ServiceResponseDTO updateService(UpdateServiceDTO updateDTO, Integer serviceId);

    ServiceResponseDTO deleteService(Integer serviceId);
}
