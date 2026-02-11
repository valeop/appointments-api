package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.bloodtype.BloodTypeResponseDTO;
import com.valeop.appointments_api.dto.bloodtype.CreateBloodTypeDTO;
import com.valeop.appointments_api.dto.bloodtype.UpdateBloodTypeDTO;
import com.valeop.appointments_api.model.BloodType;

@Component
public class BloodTypeMapper {

    private BloodTypeMapper() {
    }

    public static BloodType fromCreateBloodTypeDTO(CreateBloodTypeDTO dto) {
        BloodType bloodType = new BloodType();
        bloodType.setBloodTypeName(dto.bloodTypeName());
        return bloodType;
    }

    public static void updateFromDTO(UpdateBloodTypeDTO dto, BloodType bloodType) {
        if (!dto.bloodTypeName().isBlank()) {
            bloodType.setBloodTypeName(dto.bloodTypeName());
        }
    }

    public static BloodTypeResponseDTO toResponseDTO(BloodType bloodType) {
        return new BloodTypeResponseDTO(bloodType.getBloodTypeId(), bloodType.getBloodTypeName());
    }
}
