package com.valeop.appointments_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.servicetype.CreateServiceTypeDTO;
import com.valeop.appointments_api.dto.servicetype.ServiceTypeResponseDTO;
import com.valeop.appointments_api.dto.servicetype.UpdateServiceTypeDTO;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.ServiceTypeMapper;
import com.valeop.appointments_api.model.ServiceType;
import com.valeop.appointments_api.repository.ServiceTypeRepository;
import com.valeop.appointments_api.service.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;
    private static final String MESSAGE = "ServiceType does not exist with ID #";

    @Autowired
    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public List<ServiceTypeResponseDTO> getServiceTypesList() {
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        return serviceTypeList.stream().map(ServiceTypeMapper::toResponseDTO).toList();
    }

    @Override
    public ServiceTypeResponseDTO getServiceTypeById(Integer serviceTypeId) {
        ServiceType serviceTypeFound = serviceTypeRepository.findByServiceTypeId(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceTypeId));
        return ServiceTypeMapper.toResponseDTO(serviceTypeFound);
    }

    @Override
    public ServiceTypeResponseDTO createServiceType(CreateServiceTypeDTO createDTO) {
        ServiceType newServiceType = ServiceTypeMapper.createFromDTO(createDTO);
        return Optional.of(newServiceType).filter(s -> !s.getServiceTypeName().isBlank())
                .map(serviceTypeRepository::save)
                .map(ServiceTypeMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("serviceType should not be empty."));
    }

    @Override
    public ServiceTypeResponseDTO updateServiceType(UpdateServiceTypeDTO updateDTO, Integer serviceTypeId) {
        ServiceType serviceTypeFound = serviceTypeRepository.findByServiceTypeId(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceTypeId));

        if (!updateDTO.serviceTypeName().isBlank()) {
            serviceTypeFound.setServiceTypeName(updateDTO.serviceTypeName());
        }

        ServiceType serviceTypeUpdated = serviceTypeRepository.save(serviceTypeFound);
        return ServiceTypeMapper.toResponseDTO(serviceTypeUpdated);
    }

    @Override
    public ServiceTypeResponseDTO deleteServiceType(Integer serviceTypeId) {
        ServiceType serviceTypeFound = serviceTypeRepository.findByServiceTypeId(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceTypeId));
        serviceTypeRepository.deleteById(serviceTypeId);
        return ServiceTypeMapper.toResponseDTO(serviceTypeFound);
    }
}
