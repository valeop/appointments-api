package com.valeop.appointments_api.dto;

import java.time.LocalDate;

import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.model.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonDTO {

    private Integer personId;

    @NotNull(message = "Gender should not be empty.")
    private Gender gender;

    @NotNull(message = "Blood type should not be empty.")
    private BloodType bloodType;

    @NotBlank(message = "Identity Card should not be empty.")
    @Size(max = 20, message = "Identity card characters limit exceeded. (20)")
    private String identityCard;

    @NotBlank(message = "First name should not be empty.")
    @Size(max = 20, message = "First name characters limit exceeded. (20)")
    private String firstName;

    @NotBlank(message = "Last name should not be empty.")
    @Size(max = 20, message = "Last name characters limit exceeded. (20)")
    private String lastName;

    @NotNull(message = "Birthdate should not be empty.")
    private LocalDate birthDate;

    public PersonDTO() {
    }

    public PersonDTO(Integer personId, @NotNull(message = "Gender should not be empty.") Gender gender,
            @NotNull(message = "Blood type should not be empty.") BloodType bloodType,
            @NotBlank(message = "Identity Card should not be empty.") @Size(max = 20, message = "Identity card characters limit exceeded. (20)") String identityCard,
            @NotBlank(message = "First name should not be empty.") @Size(max = 20, message = "First name characters limit exceeded. (20)") String firstName,
            @NotBlank(message = "Last name should not be empty.") @Size(max = 20, message = "Last name characters limit exceeded. (20)") String lastName,
            @NotNull(message = "Birthdate should not be empty.") LocalDate birthDate) {
        this.personId = personId;
        this.gender = gender;
        this.bloodType = bloodType;
        this.identityCard = identityCard;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
