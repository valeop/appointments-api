package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.gender.CreateGenderDTO;
import com.valeop.appointments_api.dto.gender.GenderResponseDTO;
import com.valeop.appointments_api.dto.gender.UpdateGenderDTO;

@Service
public interface GenderService {

    List<GenderResponseDTO> getListGenders();

    GenderResponseDTO getGenderById(Integer genderId);

    GenderResponseDTO createGender(CreateGenderDTO genderDTO);

    GenderResponseDTO updateGender(UpdateGenderDTO genderDTO, Integer genderId);

    GenderResponseDTO deleteGender(Integer genderId);

}
