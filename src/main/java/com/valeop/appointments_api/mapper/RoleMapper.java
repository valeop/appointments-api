package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.role.CreateRoleDTO;
import com.valeop.appointments_api.dto.role.RoleResponseDTO;
import com.valeop.appointments_api.dto.role.UpdateRoleDTO;
import com.valeop.appointments_api.model.Role;

@Component
public class RoleMapper {

    private RoleMapper() {
    }

    public static Role fromCreateRoleDTO(CreateRoleDTO dto) {
        Role role = new Role();
        role.setRoleName(dto.roleName());
        return role;
    }

    public static void updateFromDTO(UpdateRoleDTO dto, Role role) {
        if (!dto.roleName().isBlank()) {
            role.setRoleName(dto.roleName());
        }
    }

    public static RoleResponseDTO toResponseDTO(Role role) {
        return new RoleResponseDTO(role.getRoleId(), role.getRoleName());
    }
}
