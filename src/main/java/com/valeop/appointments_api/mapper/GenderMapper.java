package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.GenderDTO;
import com.valeop.appointments_api.model.Gender;

@Component
public class GenderMapper {

    private GenderMapper() {
    }

    public static GenderDTO toDTO(Gender gender) {
        return new GenderDTO(gender.getGenderId(), gender.getGenderName());
    }

    public static Gender toGender(GenderDTO genderDTO) {
        return new Gender(genderDTO.getGenderId(), genderDTO.getGenderName());
    }
}
