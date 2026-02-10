package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.RoleDTO;

@Service
public interface RoleService {

    public List<RoleDTO> getRoleList();

    public RoleDTO getRoleById(Integer roleId);

    public RoleDTO createRole(RoleDTO roleDTO);

    public RoleDTO updateRole(RoleDTO roleDTO, Integer roleId);

    public RoleDTO deleteRole(Integer roleId);
}
