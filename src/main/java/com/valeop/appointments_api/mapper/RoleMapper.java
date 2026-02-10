package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.RoleDTO;
import com.valeop.appointments_api.model.Role;

@Component
public class RoleMapper {

    private RoleMapper() {
    }

    public static RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getRoleId(), role.getRoleName());
    }

    public static Role toEntity(RoleDTO roleDTO) {
        return new Role(roleDTO.getRoleId(), roleDTO.getRoleName());
    }
}
