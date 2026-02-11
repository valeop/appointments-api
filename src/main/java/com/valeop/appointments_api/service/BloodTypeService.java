package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.bloodtype.BloodTypeResponseDTO;
import com.valeop.appointments_api.dto.bloodtype.CreateBloodTypeDTO;
import com.valeop.appointments_api.dto.bloodtype.UpdateBloodTypeDTO;

@Service
public interface BloodTypeService {
    List<BloodTypeResponseDTO> getListBloodType();

    BloodTypeResponseDTO getBloodTypeById(Integer bloodTypeId);

    BloodTypeResponseDTO createBloodType(CreateBloodTypeDTO bloodTypeDTO);

    BloodTypeResponseDTO updateBloodType(UpdateBloodTypeDTO bloodTypeDTO, Integer bloodTypeId);

    BloodTypeResponseDTO deleteBloodType(Integer bloodTypeId);
}
