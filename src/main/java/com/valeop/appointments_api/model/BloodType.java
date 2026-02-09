package com.valeop.appointments_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;

@Entity
@Table(name = "blood_types")
public class BloodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bloodTypeId;

    @Column(name = "blood_type_name", nullable = false, unique = true)
    private String bloodTypeName;

    public BloodType() {
    }

    @Autowired
    public BloodType(Integer bloodTypeId, String bloodTypeName) {
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