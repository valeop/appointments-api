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

import com.valeop.appointments_api.dto.RoleDTO;
import com.valeop.appointments_api.service.impl.RoleServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public RoleController(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping("")
    public String isRoleWorking() {
        return "Hello, Role is working.";
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getRolesList() {
        List<RoleDTO> rolesList = roleServiceImpl.getRoleList();
        return ResponseEntity.status(HttpStatus.OK).body(rolesList);
    }

    @GetMapping("/all/{roleId}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer roleId) {
        RoleDTO roleFound = roleServiceImpl.getRoleById(roleId);

        return ResponseEntity.ok(roleFound);
    }

    @PostMapping("/create")
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleServiceImpl.createRole(roleDTO));
    }

    @PutMapping(value = "/update", params = "id")
    public ResponseEntity<RoleDTO> updateRole(@Valid @RequestBody RoleDTO roleDTO,
            @RequestParam(value = "id") Integer roleId) {
        RoleDTO roleSaved = roleServiceImpl.updateRole(roleDTO, roleId);
        return ResponseEntity.status(HttpStatus.OK).body(roleSaved);
    }

    @DeleteMapping("delete/{roleId}")
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable Integer roleId) {
        return ResponseEntity.ok(roleServiceImpl.deleteRole(roleId));
    }
}
