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

    GenderResponseDTO createGender(CreateGenderDTO createDTO);

    GenderResponseDTO updateGender(UpdateGenderDTO updateDTO, Integer genderId);

    GenderResponseDTO deleteGender(Integer genderId);

}
