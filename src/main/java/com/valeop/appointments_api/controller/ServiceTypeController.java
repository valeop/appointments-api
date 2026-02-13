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

import com.valeop.appointments_api.dto.servicetype.CreateServiceTypeDTO;
import com.valeop.appointments_api.dto.servicetype.ServiceTypeResponseDTO;
import com.valeop.appointments_api.dto.servicetype.UpdateServiceTypeDTO;
import com.valeop.appointments_api.service.impl.ServiceTypeServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicetypes")
public class ServiceTypeController {
    private final ServiceTypeServiceImpl serviceTypeServiceImpl;

    @Autowired
    public ServiceTypeController(ServiceTypeServiceImpl serviceTypeServiceImpl) {
        this.serviceTypeServiceImpl = serviceTypeServiceImpl;
    }

    @GetMapping("")
    public String isServiceTypeWorking() {
        return "Hello, ServiceType working.";
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceTypeResponseDTO>> getServiceTypesList(Integer serviceTypeId) {
        List<ServiceTypeResponseDTO> serviceTypeList = serviceTypeServiceImpl.getServiceTypesList();
        return ResponseEntity.ok(serviceTypeList);
    }

    @GetMapping("/all/{serviceTypeId}")
    public ResponseEntity<ServiceTypeResponseDTO> getServiceTypeById(@PathVariable Integer serviceTypeId) {
        ServiceTypeResponseDTO serviceTypeFound = serviceTypeServiceImpl.getServiceTypeById(serviceTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(serviceTypeFound);
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceTypeResponseDTO> createServiceType(
            @Valid @RequestBody CreateServiceTypeDTO serviceTypeDTO) {
        ServiceTypeResponseDTO serviceTypeSaved = serviceTypeServiceImpl.createServiceType(serviceTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceTypeSaved);
    }

    @PutMapping(value = "/update", params = "id")
    public ResponseEntity<ServiceTypeResponseDTO> updateServiceType(
            @Valid @RequestBody UpdateServiceTypeDTO serviceTypeDTO,
            @RequestParam(value = "id") Integer serviceTypeId) {
        ServiceTypeResponseDTO serviceTypeUpdated = serviceTypeServiceImpl.updateServiceType(serviceTypeDTO,
                serviceTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(serviceTypeUpdated);
    }

    @DeleteMapping("/delete/{serviceTypeId}")
    public ResponseEntity<ServiceTypeResponseDTO> deleteServiceType(@PathVariable Integer serviceTypeId) {
        ServiceTypeResponseDTO serviceTypeDeleted = serviceTypeServiceImpl.deleteServiceType(serviceTypeId);
        return ResponseEntity.ok(serviceTypeDeleted);
    }
}
