package com.valeop.appointments_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GenderDTO {

    private Integer genderId;

    @NotBlank(message = "Gender name should not be empty.")
    @Size(max = 20, min = 2, message = "Gender name characters limit exceeded. (20)")
    private String genderName;

    public GenderDTO(Integer genderId,
            @NotBlank(message = "Gender name should not be empty.") @Size(max = 20, min = 2, message = "Gender name characters limit exceeded. (20)") String genderName) {
        this.genderId = genderId;
        this.genderName = genderName;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
}
