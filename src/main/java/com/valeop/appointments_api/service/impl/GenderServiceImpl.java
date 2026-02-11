package com.valeop.appointments_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.dto.gender.CreateGenderDTO;
import com.valeop.appointments_api.dto.gender.GenderResponseDTO;
import com.valeop.appointments_api.dto.gender.UpdateGenderDTO;
import com.valeop.appointments_api.exceptions.BadRequestException;
import com.valeop.appointments_api.mapper.GenderMapper;
import com.valeop.appointments_api.model.Gender;
import com.valeop.appointments_api.repository.GenderRepository;
import com.valeop.appointments_api.service.GenderService;

@Service
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    @Autowired
    public GenderServiceImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public List<GenderResponseDTO> getListGenders() {
        List<Gender> genderList = genderRepository.findAll();
        return genderList.stream().map(GenderMapper::toResponseDTO).toList();
    }

    @Override
    public GenderResponseDTO getGenderById(Integer genderId) {
        Gender genderFound = genderRepository.findByGenderId(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender does not exist with ID #" + genderId));

        return GenderMapper.toResponseDTO(genderFound);
    }

    @Override
    public GenderResponseDTO createGender(CreateGenderDTO genderDTO) {
        Gender gender = GenderMapper.fromCreateGenderDTO(genderDTO);
        Gender genderSaved = Optional.of(gender)
                .filter(g -> !g.getGenderName().isBlank())
                .map(genderRepository::save)
                .orElseThrow(() -> new BadRequestException("Name should not be empty."));
        return GenderMapper.toResponseDTO(genderSaved);
    }

    @Override
    public GenderResponseDTO updateGender(UpdateGenderDTO genderDTO, Integer genderId) {
        Gender genderFound = genderRepository.findByGenderId(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender was not found with ID #" + genderId));

        GenderMapper.updateFromDTO(genderDTO, genderFound);
        genderRepository.save(genderFound);

        return GenderMapper.toResponseDTO(genderFound);
    }

    @Override
    public GenderResponseDTO deleteGender(Integer genderId) {

        Gender genderFound = genderRepository.findByGenderId(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender was not found with ID #" + genderId));
        genderRepository.deleteById(genderId);
        return GenderMapper.toResponseDTO(genderFound);
    }
}