package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.service.CreateServiceDTO;
import com.valeop.appointments_api.dto.service.ServiceResponseDTO;
import com.valeop.appointments_api.dto.service.UpdateServiceDTO;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.ServiceMapper;
import com.valeop.appointments_api.model.ServiceType;
import com.valeop.appointments_api.repository.ServiceRepository;
import com.valeop.appointments_api.repository.ServiceTypeRepository;
import com.valeop.appointments_api.service.ServiceService;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private static final String MESSAGE = "Service does not exist with ID #";

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceTypeRepository serviceTypeRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public List<ServiceResponseDTO> getServicesList() {
        return serviceRepository.findAll()
                .stream().map(ServiceMapper::toResponseDTO).toList();
    }

    @Override
    public ServiceResponseDTO getServiceById(Integer serviceId) {
        com.valeop.appointments_api.model.Service serviceFound = serviceRepository.findByServiceId(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceId));

        return ServiceMapper.toResponseDTO(serviceFound);
    }

    @Override
    public ServiceResponseDTO createService(CreateServiceDTO createDTO) {
        ServiceType serviceTypeFound = serviceTypeRepository
                .findByServiceTypeId(createDTO.serviceType().getServiceTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ServiceType does not exist. Try another one"));

        com.valeop.appointments_api.model.Service newService = ServiceMapper.createFromDTO(createDTO,
                serviceTypeFound);

        com.valeop.appointments_api.model.Service serviceSaved = serviceRepository.save(newService);
        return ServiceMapper.toResponseDTO(serviceSaved);
    }

    @Override
    public ServiceResponseDTO updateService(UpdateServiceDTO updateDTO, Integer serviceId) {
        com.valeop.appointments_api.model.Service serviceFound = serviceRepository.findByServiceId(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceId));

        if (updateDTO.serviceType() != null) {
            Integer serviceTypeId = updateDTO.serviceType().getServiceTypeId();
            ServiceType serviceTypeFound = serviceTypeRepository
                    .findByServiceTypeId(serviceTypeId)
                    .orElseThrow(() -> new ResourceNotFoundException("serviceType does not exist. Try another one"));

            serviceFound.setServiceType(serviceTypeFound);
        }

        ServiceMapper.updateFromDTO(updateDTO, serviceFound);
        com.valeop.appointments_api.model.Service serviceUpdated = serviceRepository.save(serviceFound);
        return ServiceMapper.toResponseDTO(serviceUpdated);
    }

    @Override
    public ServiceResponseDTO deleteService(Integer serviceId) {
        com.valeop.appointments_api.model.Service serviceFound = serviceRepository.findByServiceId(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceId));
        serviceRepository.deleteById(serviceId);
        return ServiceMapper.toResponseDTO(serviceFound);
    }
}
