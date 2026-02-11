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

import com.valeop.appointments_api.dto.gender.CreateGenderDTO;
import com.valeop.appointments_api.dto.gender.GenderResponseDTO;
import com.valeop.appointments_api.dto.gender.UpdateGenderDTO;
import com.valeop.appointments_api.service.impl.GenderServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genders")
public class GenderController {
    private final GenderServiceImpl genderServiceImpl;

    @Autowired
    public GenderController(GenderServiceImpl genderServiceImpl) {
        this.genderServiceImpl = genderServiceImpl;
    }

    @GetMapping("")
    String isGenderWorking() {
        return "Hello, Gender working.";
    }

    @GetMapping("/all")
    ResponseEntity<List<GenderResponseDTO>> getAllGenders() {
        List<GenderResponseDTO> resultList = genderServiceImpl.getListGenders();
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @GetMapping(value = "/all", params = "id")
    ResponseEntity<GenderResponseDTO> getGenderById(@RequestParam(value = "id") Integer genderId) {
        GenderResponseDTO genderFound = genderServiceImpl.getGenderById(genderId);
        return ResponseEntity.status(HttpStatus.OK).body(genderFound);
    }

    @PostMapping("/create")
    ResponseEntity<GenderResponseDTO> createGender(@Valid @RequestBody CreateGenderDTO genderDTO) {
        GenderResponseDTO genderSaved = genderServiceImpl.createGender(genderDTO);
        return ResponseEntity.ok(genderSaved);
    }

    @PutMapping(value = "/update", params = "id")
    ResponseEntity<GenderResponseDTO> updateGender(@Valid @RequestBody UpdateGenderDTO genderDTO,
            @RequestParam(value = "id") Integer genderId) {
        GenderResponseDTO genderSaved = genderServiceImpl.updateGender(genderDTO, genderId);

        return ResponseEntity.status(HttpStatus.OK).body(genderSaved);
    }

    @DeleteMapping("/delete/{genderId}")
    ResponseEntity<GenderResponseDTO> deleteGender(@PathVariable Integer genderId) {
        GenderResponseDTO genderDeleted = genderServiceImpl.deleteGender(genderId);
        return ResponseEntity.ok(genderDeleted);
    }
}
