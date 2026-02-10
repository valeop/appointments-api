package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.BloodTypeDTO;
import com.valeop.appointments_api.model.BloodType;

@Component
public class BloodTypeMapper {

    private BloodTypeMapper() {
    }

    public static BloodTypeDTO toDTO(BloodType bloodType) {
        return new BloodTypeDTO(bloodType.getBloodTypeId(), bloodType.getBloodTypeName());
    }

    public static BloodType toEntity(BloodTypeDTO bloodTypeDTO) {
        return new BloodType(bloodTypeDTO.getBloodTypeId(), bloodTypeDTO.getBloodTypeName());
    }
}
