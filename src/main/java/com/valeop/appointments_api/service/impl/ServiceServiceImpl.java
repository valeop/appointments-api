package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.service.CreateServiceDTO;
import com.valeop.appointments_api.dto.service.ServiceResponseDTO;
import com.valeop.appointments_api.dto.service.UpdateServiceDTO;
import com.valeop.appointments_api.exceptions.BadRequestException;
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
        if (createDTO.serviceType() == null) {
            throw new BadRequestException("serviceType should not be empty");
        }
        Integer serviceTypeId = createDTO.serviceType().getServiceTypeId();
        if (!serviceTypeRepository.existsById(serviceTypeId)) {
            throw new ResourceNotFoundException("serviceType does not exist. Try another one");
        }
        ServiceType serviceTypeFound = serviceTypeRepository.getReferenceById(serviceTypeId);
        com.valeop.appointments_api.model.Service newService = ServiceMapper.fromCreateServiceDTO(createDTO,
                serviceTypeFound);

        serviceRepository.save(newService);
        return ServiceMapper.toResponseDTO(newService);
    }

    @Override
    public ServiceResponseDTO updateService(UpdateServiceDTO updateDTO, Integer serviceId) {
        com.valeop.appointments_api.model.Service serviceFound = serviceRepository.findByServiceId(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceId));

        if (updateDTO.serviceType() != null) {
            Integer serviceTypeId = updateDTO.serviceType().getServiceTypeId();
            if (!serviceRepository.existsById(serviceTypeId)) {
                throw new ResourceNotFoundException("serviceType does not exist with ID #" + serviceTypeId);
            }
            serviceFound.setServiceType(serviceTypeRepository.getReferenceById(serviceTypeId));
        }
        ServiceMapper.updateFromDTO(updateDTO, serviceFound);
        serviceRepository.save(serviceFound);

        return ServiceMapper.toResponseDTO(serviceFound);
    }

    @Override
    public ServiceResponseDTO deleteService(Integer serviceId) {
        com.valeop.appointments_api.model.Service serviceFound = serviceRepository.findByServiceId(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceId));
        serviceRepository.deleteById(serviceId);
        return ServiceMapper.toResponseDTO(serviceFound);
    }
}
