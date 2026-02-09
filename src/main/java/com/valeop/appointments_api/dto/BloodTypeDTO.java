package com.valeop.appointments_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BloodTypeDTO {

    private Integer bloodTypeId;

    @NotBlank(message = "BloodType name should not be empty.")
    @Size(max = 20, min = 2, message = "BloodType name characters limit exceeded. (20)")
    private String bloodTypeName;

    public BloodTypeDTO(Integer bloodTypeId,
            @NotBlank(message = "BloodType name should not be empty.") @Size(max = 20, min = 2, message = "BloodType name characters limit exceeded. (20)") String bloodTypeName) {
        this.bloodTypeId = bloodTypeId;
        this.bloodTypeName = bloodTypeName;
    }

    public Integer getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(Integer bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public String getBloodTypeName() {
        return bloodTypeName;
    }

    public void setBloodTypeName(String bloodTypeName) {
        this.bloodTypeName = bloodTypeName;
    }
}
