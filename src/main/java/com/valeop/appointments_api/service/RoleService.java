package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.role.CreateRoleDTO;
import com.valeop.appointments_api.dto.role.RoleResponseDTO;
import com.valeop.appointments_api.dto.role.UpdateRoleDTO;

@Service
public interface RoleService {

    public List<RoleResponseDTO> getRoleList();

    public RoleResponseDTO getRoleById(Integer roleId);

    public RoleResponseDTO createRole(CreateRoleDTO createDTO);

    public RoleResponseDTO updateRole(UpdateRoleDTO updateDTO, Integer roleId);

    public RoleResponseDTO deleteRole(Integer roleId);
}
