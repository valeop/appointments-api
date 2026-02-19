package com.valeop.appointments_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.valeop.appointments_api.dto.service.CreateServiceDTO;
import com.valeop.appointments_api.dto.service.ServiceResponseDTO;
import com.valeop.appointments_api.dto.service.UpdateServiceDTO;
import com.valeop.appointments_api.service.impl.ServiceServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceServiceImpl serviceServiceImpl;

    @Autowired
    public ServiceController(ServiceServiceImpl serviceServiceImpl) {
        this.serviceServiceImpl = serviceServiceImpl;
    }

    @GetMapping("")
    public String isServiceWorking() {
        return "Hello, Service working.";
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceResponseDTO>> getServicesList() {
        List<ServiceResponseDTO> serviceList = serviceServiceImpl.getServicesList();
        return ResponseEntity.status(HttpStatus.OK).body(serviceList);
    }

    @GetMapping("/all/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable Integer serviceId) {
        ServiceResponseDTO serviceFound = serviceServiceImpl.getServiceById(serviceId);
        return ResponseEntity.ok(serviceFound);
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceResponseDTO> createService(@Valid @RequestBody CreateServiceDTO createDTO) {
        ServiceResponseDTO serviceSaved = serviceServiceImpl.createService(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceSaved);
    }

    @PutMapping(value = "/update", params = "id")
    public ResponseEntity<ServiceResponseDTO> updateService(@Valid @RequestBody UpdateServiceDTO updateDTO,
            @RequestParam(value = "id") Integer serviceId) {
        ServiceResponseDTO serviceUpdated = serviceServiceImpl.updateService(updateDTO, serviceId);
        return ResponseEntity.ok(serviceUpdated);
    }

    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> deleteService(@PathVariable Integer serviceId) {
        ServiceResponseDTO serviceDeleted = serviceServiceImpl.deleteService(serviceId);
        return ResponseEntity.status(HttpStatus.OK).body(serviceDeleted);
    }
}
