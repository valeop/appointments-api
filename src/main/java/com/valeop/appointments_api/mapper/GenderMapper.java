package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.gender.CreateGenderDTO;
import com.valeop.appointments_api.dto.gender.GenderResponseDTO;
import com.valeop.appointments_api.model.Gender;

@Component
public class GenderMapper {

    private GenderMapper() {
    }

    public static Gender createFromDTO(CreateGenderDTO dto) {
        Gender gender = new Gender();
        gender.setGenderName(dto.genderName());
        return gender;
    }

    public static GenderResponseDTO toResponseDTO(Gender gender) {
        return new GenderResponseDTO(gender.getGenderId(), gender.getGenderName());
    }
}
