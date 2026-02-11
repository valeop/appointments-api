package com.valeop.appointments_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.bloodtype.BloodTypeResponseDTO;
import com.valeop.appointments_api.dto.bloodtype.CreateBloodTypeDTO;
import com.valeop.appointments_api.dto.bloodtype.UpdateBloodTypeDTO;
import com.valeop.appointments_api.exceptions.BadRequestException;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.BloodTypeMapper;
import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.repository.BloodTypeRepository;
import com.valeop.appointments_api.service.BloodTypeService;

@Service
public class BloodTypeServiceImpl implements BloodTypeService {
    private final BloodTypeRepository bloodTypeRepository;
    static final String MESSAGE = "BloodType not found with ID #";

    @Autowired
    public BloodTypeServiceImpl(BloodTypeRepository bloodTypeRepository) {
        this.bloodTypeRepository = bloodTypeRepository;
    }

    @Override
    public List<BloodTypeResponseDTO> getListBloodType() {
        List<BloodType> bloodTypeList = bloodTypeRepository.findAll();
        return bloodTypeList.stream().map(BloodTypeMapper::toResponseDTO).toList();
    }

    @Override
    public BloodTypeResponseDTO getBloodTypeById(Integer bloodTypeId) {
        BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(bloodTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + bloodTypeId));

        return BloodTypeMapper.toResponseDTO(bloodTypeFound);
    }

    @Override
    public BloodTypeResponseDTO createBloodType(CreateBloodTypeDTO bloodTypeDTO) {
        BloodType bloodType = BloodTypeMapper.fromCreateBloodTypeDTO(bloodTypeDTO);
        BloodType bloodTypeSaved = Optional.of(bloodType)
                .filter(b -> !b.getBloodTypeName().isBlank())
                .map(bloodTypeRepository::save)
                .orElseThrow(() -> new BadRequestException("BloodTypeName shouldn't be empty."));
        return BloodTypeMapper.toResponseDTO(bloodTypeSaved);
    }

    @Override
    public BloodTypeResponseDTO updateBloodType(UpdateBloodTypeDTO bloodTypeDTO, Integer bloodTypeId) {
        BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(bloodTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + bloodTypeId));
        BloodTypeMapper.updateFromDTO(bloodTypeDTO, bloodTypeFound);
        bloodTypeRepository.save(bloodTypeFound);
        return BloodTypeMapper.toResponseDTO(bloodTypeFound);
    }

    @Override
    public BloodTypeResponseDTO deleteBloodType(Integer bloodTypeId) {
        BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(bloodTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + bloodTypeId));
        bloodTypeRepository.deleteById(bloodTypeId);
        return BloodTypeMapper.toResponseDTO(bloodTypeFound);
    }
}