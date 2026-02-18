package com.valeop.appointments_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valeop.appointments_api.dto.doctorservice.CreateDoctorServiceDTO;
import com.valeop.appointments_api.dto.doctorservice.DoctorServiceResponseDTO;
import com.valeop.appointments_api.dto.doctorservice.UpdateDoctorServiceDTO;
import com.valeop.appointments_api.service.impl.DoctorServiceServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/doctorservices")
public class DoctorServiceController {
    private final DoctorServiceServiceImpl doctorServiceServiceImpl;

    public DoctorServiceController(DoctorServiceServiceImpl doctorServiceServiceImpl) {
        this.doctorServiceServiceImpl = doctorServiceServiceImpl;
    }

    @GetMapping("")
    public String isDoctorService() {
        return "Hello, DoctorService working.";
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorServiceResponseDTO>> getDoctorServiceslist() {
        List<DoctorServiceResponseDTO> doctorServicesList = doctorServiceServiceImpl.getDoctorServicesList();
        return ResponseEntity.ok(doctorServicesList);
    }

    @GetMapping("/all/{doctorServiceId}")
    public ResponseEntity<DoctorServiceResponseDTO> getDoctorServiceById(@PathVariable Integer doctorServiceId) {
        DoctorServiceResponseDTO doctorServiceFound = doctorServiceServiceImpl.getDoctorServiceById(doctorServiceId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorServiceFound);
    }

    @PostMapping("/create")
    public ResponseEntity<DoctorServiceResponseDTO> createDoctorService(
            @Valid @RequestBody CreateDoctorServiceDTO createDTO) {
        DoctorServiceResponseDTO doctorServiceSaved = doctorServiceServiceImpl.createDoctorService(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorServiceSaved);
    }

    @PutMapping(value = "/update", params = "id")
    public ResponseEntity<DoctorServiceResponseDTO> updateDoctorService(
            @Valid @RequestBody UpdateDoctorServiceDTO updateDTO, @RequestParam(value = "id") Integer doctorServiceId) {
        DoctorServiceResponseDTO doctorServiceUpdated = doctorServiceServiceImpl.updateDoctorService(updateDTO,
                doctorServiceId);
        return ResponseEntity.ok(doctorServiceUpdated);
    }

    @DeleteMapping("/delete/{doctorServiceId}")
    public ResponseEntity<DoctorServiceResponseDTO> deleteDoctorService(@PathVariable Integer doctorServiceId) {
        DoctorServiceResponseDTO doctorServiceDeleted = doctorServiceServiceImpl.deleteDoctorService(doctorServiceId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorServiceDeleted);
    }

}
