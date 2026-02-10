package com.valeop.appointments_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.GenderDTO;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
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
    public List<GenderDTO> getListGenders() {
        List<Gender> genderList = genderRepository.findAll();
        return genderList.stream().map(GenderMapper::toDTO).toList();
    }

    @Override
    public GenderDTO getGenderById(Integer genderId) {
        Gender genderFound = genderRepository.findByGenderId(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender does not exist with ID #" + genderId));

        return GenderMapper.toDTO(genderFound);
    }

    @Override
    public GenderDTO createGender(GenderDTO genderDTO) {
        Gender gender = GenderMapper.toGender(genderDTO);
        Gender genderSaved = Optional.of(gender)
                .filter(g -> !g.getGenderName().isBlank())
                .map(genderRepository::save)
                .orElseThrow(() -> new BadRequestException("Name should not be empty."));
        return GenderMapper.toDTO(genderSaved);
    }

    @Override
    public GenderDTO updateGender(GenderDTO genderDTO, Integer genderId) {
        Gender genderFound = genderRepository.findByGenderId(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender was not found with ID #" + genderId));

        Gender genderUpdated = GenderMapper.toGender(genderDTO);
        genderFound.setGenderName(genderUpdated.getGenderName());
        genderRepository.save(genderFound);

        return GenderMapper.toDTO(genderFound);
    }

    @Override
    public GenderDTO deleteGender(Integer genderId) {

        Gender genderFound = genderRepository.findByGenderId(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender was not found with ID #" + genderId));
        genderRepository.deleteById(genderId);
        return GenderMapper.toDTO(genderFound);
    }
}
