package com.valeop.appointments_api.model.enums;

import java.util.Set;

public enum ServiceType {
    GENERAL("General"),
    DENTAL("Dental"),
    OPHTHALMOLOGY("Ophthalmology"),
    SPECIALIST("Specialist"),
    LABORATORY("Laboratory"),
    DIAGNOSTIC_RADIOLOGY("Diagnostic radiology");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final Set<String> PATIENT_ALLOWED = Set.of(
            GENERAL.value, DENTAL.value, OPHTHALMOLOGY.value);
}