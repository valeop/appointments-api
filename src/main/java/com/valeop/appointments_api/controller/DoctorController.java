package com.valeop.appointments_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valeop.appointments_api.dto.doctor.CreateDoctorDTO;
import com.valeop.appointments_api.dto.doctor.DoctorResponseDTO;
import com.valeop.appointments_api.dto.doctor.UpdateDoctorDTO;
import com.valeop.appointments_api.service.impl.DoctorServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping("")
    public String isDoctorWorking() {
        return "Hello, Doctor working.";
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDTO>> getDoctorsList() {
        List<DoctorResponseDTO> doctorList = doctorServiceImpl.getDoctorsList();
        return ResponseEntity.ok(doctorList);
    }

    @GetMapping("/all/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Integer doctorId) {
        DoctorResponseDTO doctorFound = doctorServiceImpl.getDoctorById(doctorId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorFound);
    }

    @PostMapping("/create")
    public ResponseEntity<DoctorResponseDTO> createDoctor(@Valid @RequestBody CreateDoctorDTO createDoctorDTO) {
        DoctorResponseDTO doctorSaved = doctorServiceImpl.createDoctor(createDoctorDTO);
        return ResponseEntity.ok(doctorSaved);
    }

    @PutMapping(value = "/update", params = "id")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@Valid @RequestBody UpdateDoctorDTO updateDoctorDTO,
            @RequestParam(value = "id") Integer doctorId) {
        DoctorResponseDTO doctorSaved = doctorServiceImpl.updateDoctor(updateDoctorDTO, doctorId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorSaved);
    }

    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> deleteDoctor(@PathVariable Integer doctorId) {
        DoctorResponseDTO doctorDeleted = doctorServiceImpl.deleteDoctor(doctorId);
        return ResponseEntity.ok(doctorDeleted);
    }
}
