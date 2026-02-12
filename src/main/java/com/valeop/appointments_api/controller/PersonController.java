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

import com.valeop.appointments_api.dto.person.CreatePersonDTO;
import com.valeop.appointments_api.dto.person.PersonResponseDTO;
import com.valeop.appointments_api.dto.person.UpdatePersonDTO;
import com.valeop.appointments_api.service.impl.PersonServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonServiceImpl personServiceImpl;

    @Autowired
    public PersonController(PersonServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @GetMapping("")
    public String isPersonWorking() {
        return "Hello, Person working.";
    }

    @GetMapping("/list")
    public ResponseEntity<List<PersonResponseDTO>> getList() {
        List<PersonResponseDTO> personsList = personServiceImpl.getPersonList();
        return ResponseEntity.status(HttpStatus.OK).body(personsList);
    }

    @GetMapping(value = "/list", params = "id")
    public ResponseEntity<PersonResponseDTO> getPersonById(@RequestParam(value = "id") Integer personId) {
        PersonResponseDTO personExisting = personServiceImpl.getPersonById(personId);
        return ResponseEntity.status(HttpStatus.OK).body(personExisting);
    }

    @GetMapping(value = "/list", params = "id-card")
    public ResponseEntity<PersonResponseDTO> getPersonByIdentityCard(
            @RequestParam(value = "id-card") String identityCard) {
        PersonResponseDTO personExisting = personServiceImpl.getPersonByIdentityCard(identityCard);
        return new ResponseEntity<>(personExisting, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PersonResponseDTO> createPerson(@Valid @RequestBody CreatePersonDTO person) {
        PersonResponseDTO newPerson = personServiceImpl.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
    }

    @PutMapping("/update/{personId}")
    public ResponseEntity<PersonResponseDTO> updatePerson(@Valid @RequestBody UpdatePersonDTO person,
            @PathVariable Integer personId) {
        PersonResponseDTO personBBDD = personServiceImpl.updatePerson(person, personId);
        return ResponseEntity.ok(personBBDD);
    }

    @DeleteMapping("/delete/{personId}")
    public ResponseEntity<PersonResponseDTO> deletePerson(@PathVariable Integer personId) {
        PersonResponseDTO personDeleted = personServiceImpl.deletePerson(personId);
        return ResponseEntity.ok(personDeleted);
    }
}
