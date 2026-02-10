package com.valeop.appointments_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.BloodTypeDTO;
import com.valeop.appointments_api.exceptions.BadRequestException;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.BloodTypeMapper;
import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.repository.BloodTypeRepository;
import com.valeop.appointments_api.service.BloodTypeService;

@Service
public class BloodTypeServiceImpl implements BloodTypeService {
    private final BloodTypeRepository bloodTypeRepository;

    @Autowired
    public BloodTypeServiceImpl(BloodTypeRepository bloodTypeRepository) {
        this.bloodTypeRepository = bloodTypeRepository;
    }

    @Override
    public List<BloodTypeDTO> getListBloodType() {
        List<BloodType> bloodTypeList = bloodTypeRepository.findAll();
        return bloodTypeList.stream().map(BloodTypeMapper::toDTO).toList();
    }

    @Override
    public BloodTypeDTO getBloodTypeById(Integer bloodTypeId) {
        BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(bloodTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("BloodType not found with ID #" + bloodTypeId));

        return BloodTypeMapper.toDTO(bloodTypeFound);
    }

    @Override
    public BloodTypeDTO createBloodType(BloodTypeDTO bloodTypeDTO) {
        BloodType bloodType = BloodTypeMapper.toEntity(bloodTypeDTO);
        BloodType bloodTypeSaved = Optional.of(bloodType)
                .filter(b -> !b.getBloodTypeName().isBlank())
                .map(bloodTypeRepository::save)
                .orElseThrow(() -> new BadRequestException("BloodTypeName shouldn't be empty."));
        return BloodTypeMapper.toDTO(bloodTypeSaved);
    }

    @Override
    public BloodTypeDTO updateBloodType(BloodTypeDTO bloodTypeDTO, Integer bloodTypeId) {
        BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(bloodTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("BloodType not found with ID #" + bloodTypeId));
        bloodTypeFound.setBloodTypeName(bloodTypeDTO.getBloodTypeName());
        bloodTypeRepository.save(bloodTypeFound);
        return BloodTypeMapper.toDTO(bloodTypeFound);
    }

    @Override
    public BloodTypeDTO deleteBloodType(Integer bloodTypeId) {
        BloodType bloodTypeFound = bloodTypeRepository.findByBloodTypeId(bloodTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("BloodType not found woth ID #" + bloodTypeId));
        bloodTypeRepository.deleteById(bloodTypeId);
        return BloodTypeMapper.toDTO(bloodTypeFound);
    }
}
