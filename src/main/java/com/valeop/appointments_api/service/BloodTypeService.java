package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.BloodTypeDTO;

@Service
public interface BloodTypeService {
    List<BloodTypeDTO> getListBloodType();

    BloodTypeDTO getBloodTypeById(Integer bloodTypeId);

    BloodTypeDTO createBloodType(BloodTypeDTO bloodTypeDTO);

    BloodTypeDTO updateBloodType(BloodTypeDTO bloodTypeDTO, Integer bloodTypeId);

    BloodTypeDTO deleteBloodType(Integer bloodTypeId);
}
