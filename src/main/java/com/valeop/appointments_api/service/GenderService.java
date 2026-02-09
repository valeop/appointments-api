package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.GenderDTO;

@Service
public interface GenderService {

    List<GenderDTO> getListGenders();

    GenderDTO getGenderById(Integer genderId);

    GenderDTO createGender(GenderDTO genderDTO);

    GenderDTO updateGender(GenderDTO genderDTO, Integer genderId);

    GenderDTO deleteGender(Integer genderId);

}
