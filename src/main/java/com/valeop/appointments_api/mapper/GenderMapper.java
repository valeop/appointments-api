package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.gender.CreateGenderDTO;
import com.valeop.appointments_api.dto.gender.GenderResponseDTO;
import com.valeop.appointments_api.dto.gender.UpdateGenderDTO;
import com.valeop.appointments_api.model.Gender;

@Component
public class GenderMapper {

    private GenderMapper() {
    }

    public static Gender fromCreateGenderDTO(CreateGenderDTO dto) {
        Gender gender = new Gender();
        gender.setGenderName(dto.genderName());
        return gender;
    }

    public static void updateFromDTO(UpdateGenderDTO dto, Gender gender) {
        if (!dto.genderName().isBlank()) {
            gender.setGenderName(dto.genderName());
        }
    }

    public static GenderResponseDTO toResponseDTO(Gender gender) {
        return new GenderResponseDTO(gender.getGenderId(), gender.getGenderName());
    }
}
