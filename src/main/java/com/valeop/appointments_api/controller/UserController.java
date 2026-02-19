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

import com.valeop.appointments_api.dto.user.CreateUserDTO;
import com.valeop.appointments_api.dto.user.UpdatePasswordDTO;
import com.valeop.appointments_api.dto.user.UpdateUserDTO;
import com.valeop.appointments_api.dto.user.UserResponseDTO;
import com.valeop.appointments_api.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("")
    public String isUserWorking() {
        return "Hello, User is working.";
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getUsersList() {
        List<UserResponseDTO> userList = userServiceImpl.getUsersList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer userId) {
        UserResponseDTO userFound = userServiceImpl.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userFound);
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO createDTO) {
        UserResponseDTO userSaved = userServiceImpl.createUser(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @PutMapping(value = "/update", params = "id")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateDTO,
            @RequestParam(value = "id") Integer userId) {
        UserResponseDTO userUpdated = userServiceImpl.updateUser(updateDTO, userId);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @PutMapping(value = "/update/password", params = "id")
    public ResponseEntity<UserResponseDTO> updateUserPassword(@Valid @RequestBody UpdatePasswordDTO updateDTO,
            @RequestParam(value = "id") Integer userId) {
        UserResponseDTO userUpdated = userServiceImpl.updateUserPassword(updateDTO, userId);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Integer userId) {
        UserResponseDTO userDeleted = userServiceImpl.deleteUser(userId);
        return ResponseEntity.ok(userDeleted);
    }
}
