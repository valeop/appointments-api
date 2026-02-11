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

import com.valeop.appointments_api.dto.bloodtype.BloodTypeResponseDTO;
import com.valeop.appointments_api.dto.bloodtype.CreateBloodTypeDTO;
import com.valeop.appointments_api.dto.bloodtype.UpdateBloodTypeDTO;
import com.valeop.appointments_api.service.impl.BloodTypeServiceImpl;

import jakarta.validation.Valid;

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
        return ResponseEntity.ok(bloodTypeServiceImpl.getBloodTypeById(bloodTypeId));
    }

    @PostMapping("/create")
    ResponseEntity<BloodTypeResponseDTO> createBloodType(@Valid @RequestBody CreateBloodTypeDTO bloodTypeDTO) {
        BloodTypeResponseDTO bloodTypeSaved = bloodTypeServiceImpl.createBloodType(bloodTypeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(bloodTypeSaved);
    }

    @PutMapping(value = "/update", params = "id")
    ResponseEntity<BloodTypeResponseDTO> updateBloodType(@Valid @RequestBody UpdateBloodTypeDTO bloodTypeDTO,
            @RequestParam(value = "id") Integer bloodTypeId) {
        BloodTypeResponseDTO bloodTypeSaved = bloodTypeServiceImpl.updateBloodType(bloodTypeDTO, bloodTypeId);
        return ResponseEntity.ok(bloodTypeSaved);
    }

    @DeleteMapping("/delete/{bloodTypeId}")
    ResponseEntity<BloodTypeResponseDTO> deleteBloodType(@PathVariable Integer bloodTypeId) {
        BloodTypeResponseDTO bloodTypeDeleted = bloodTypeServiceImpl.deleteBloodType(bloodTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(bloodTypeDeleted);
    }
}
