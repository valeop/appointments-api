package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.role.CreateRoleDTO;
import com.valeop.appointments_api.dto.role.RoleResponseDTO;
import com.valeop.appointments_api.model.Role;

@Component
public class RoleMapper {

    private RoleMapper() {
    }

    public static Role createFromDTO(CreateRoleDTO dto) {
        Role role = new Role();
        role.setRoleName(dto.roleName());
        return role;
    }

    public static RoleResponseDTO toResponseDTO(Role role) {
        return new RoleResponseDTO(role.getRoleId(), role.getRoleName());
    }
}
