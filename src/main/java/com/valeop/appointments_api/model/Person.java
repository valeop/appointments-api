package com.valeop.appointments_api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;

    @Column(name = "identity_card", nullable = false, unique = true)
    private String identityCard;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "blood_type_id", nullable = false)
    private BloodType bloodType;

    public Person() {
    }

    public Person(Integer personId, Gender gender, BloodType bloodType,
            String identityCard, String firstName, String lastName, LocalDate birthDate) {
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