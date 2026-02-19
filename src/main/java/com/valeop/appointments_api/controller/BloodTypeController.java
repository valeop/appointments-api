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

import com.valeop.appointments_api.dto.bloodtype.BloodTypeResponseDTO;
import com.valeop.appointments_api.dto.bloodtype.CreateBloodTypeDTO;
import com.valeop.appointments_api.dto.bloodtype.UpdateBloodTypeDTO;
import com.valeop.appointments_api.service.impl.BloodTypeServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bloodtypes")
public class BloodTypeController {
    private final BloodTypeServiceImpl bloodTypeServiceImpl;

    @Autowired
    public BloodTypeController(BloodTypeServiceImpl bloodTypeServiceImpl) {
        this.bloodTypeServiceImpl = bloodTypeServiceImpl;
    }

    @GetMapping("")
    String isBloodTypeWorking() {
        return "Hello, BloodType working.";
    }

    @GetMapping("/all")
    ResponseEntity<List<BloodTypeResponseDTO>> getAllBloodTypes() {
        List<BloodTypeResponseDTO> bloodTypeList = bloodTypeServiceImpl.getListBloodType();
        return ResponseEntity.status(HttpStatus.OK).body(bloodTypeList);
    }

    @GetMapping("/all/{bloodTypeId}")
    ResponseEntity<BloodTypeResponseDTO> getBloodTypeById(@PathVariable Integer bloodTypeId) {
        BloodTypeResponseDTO bloodTypeFound = bloodTypeServiceImpl.getBloodTypeById(bloodTypeId);
        return ResponseEntity.ok(bloodTypeFound);
    }

    @PostMapping("/create")
    ResponseEntity<BloodTypeResponseDTO> createBloodType(@Valid @RequestBody CreateBloodTypeDTO createDTO) {
        BloodTypeResponseDTO bloodTypeSaved = bloodTypeServiceImpl.createBloodType(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bloodTypeSaved);
    }

    @PutMapping(value = "/update", params = "id")
    ResponseEntity<BloodTypeResponseDTO> updateBloodType(@Valid @RequestBody UpdateBloodTypeDTO updateDTO,
            @RequestParam(value = "id") Integer bloodTypeId) {
        BloodTypeResponseDTO bloodTypeUpdated = bloodTypeServiceImpl.updateBloodType(updateDTO, bloodTypeId);
        return ResponseEntity.ok(bloodTypeUpdated);
    }

    @DeleteMapping("/delete/{bloodTypeId}")
    ResponseEntity<BloodTypeResponseDTO> deleteBloodType(@PathVariable Integer bloodTypeId) {
        BloodTypeResponseDTO bloodTypeDeleted = bloodTypeServiceImpl.deleteBloodType(bloodTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(bloodTypeDeleted);
    }
}
