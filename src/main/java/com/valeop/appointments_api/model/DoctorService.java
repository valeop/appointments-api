package com.valeop.appointments_api.model;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor_services")
public class DoctorService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorServiceId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    public DoctorService() {
    }

    @Autowired
    public DoctorService(Integer doctorServiceId, Doctor doctor, Service service) {
        this.doctorServiceId = doctorServiceId;
        this.doctor = doctor;
        this.service = service;
    }

    public Integer getDoctorServiceId() {
        return doctorServiceId;
    }

    public void setDoctorServiceId(Integer doctorServiceId) {
        this.doctorServiceId = doctorServiceId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
