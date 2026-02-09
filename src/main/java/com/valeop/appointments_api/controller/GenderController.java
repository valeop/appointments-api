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

import com.valeop.appointments_api.dto.GenderDTO;
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
    ResponseEntity<List<GenderDTO>> getAllGenders() {
        List<GenderDTO> resultList = genderServiceImpl.getListGenders();
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @GetMapping(value = "/all", params = "id")
    ResponseEntity<GenderDTO> getGenderById(@RequestParam(value = "id") Integer genderId) {
        GenderDTO genderFound = genderServiceImpl.getGenderById(genderId);
        return ResponseEntity.status(HttpStatus.OK).body(genderFound);
    }

    @PostMapping("/create")
    ResponseEntity<GenderDTO> createGender(@Valid @RequestBody GenderDTO genderDTO) {
        GenderDTO genderSaved = genderServiceImpl.createGender(genderDTO);
        return ResponseEntity.ok(genderSaved);
    }

    @PutMapping(value = "/update", params = "id")
    ResponseEntity<GenderDTO> updateGender(@Valid @RequestBody GenderDTO genderDTO,
            @RequestParam(value = "id") Integer genderId) {
        GenderDTO genderSaved = genderServiceImpl.updateGender(genderDTO, genderId);

        return ResponseEntity.status(HttpStatus.OK).body(genderSaved);
    }

    @DeleteMapping("/delete/{genderId}")
    ResponseEntity<GenderDTO> deleteGender(@PathVariable Integer genderId) {
        GenderDTO genderDeleted = genderServiceImpl.deleteGender(genderId);
        return ResponseEntity.ok(genderDeleted);
    }
}
