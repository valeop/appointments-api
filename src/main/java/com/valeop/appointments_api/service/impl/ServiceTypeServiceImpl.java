package com.valeop.appointments_api.service.impl;

import java.util.List;

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
    public ServiceTypeResponseDTO createServiceType(CreateServiceTypeDTO serviceTypeDTO) {
        ServiceType newServiceType = ServiceTypeMapper.fromCreateServiceTypeDTO(serviceTypeDTO);
        serviceTypeRepository.save(newServiceType);
        return ServiceTypeMapper.toResponseDTO(newServiceType);
    }

    @Override
    public ServiceTypeResponseDTO updateServiceType(UpdateServiceTypeDTO serviceTypeDTO, Integer serviceTypeId) {
        ServiceType serviceTypeFound = serviceTypeRepository.findByServiceTypeId(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceTypeId));

        ServiceTypeMapper.updateFromDTO(serviceTypeDTO, serviceTypeFound);
        serviceTypeRepository.save(serviceTypeFound);
        return ServiceTypeMapper.toResponseDTO(serviceTypeFound);
    }

    @Override
    public ServiceTypeResponseDTO deleteServiceType(Integer serviceTypeId) {
        ServiceType serviceTypeFound = serviceTypeRepository.findByServiceTypeId(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + serviceTypeId));
        serviceTypeRepository.deleteById(serviceTypeId);
        return ServiceTypeMapper.toResponseDTO(serviceTypeFound);
    }
}
