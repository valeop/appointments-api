package com.valeop.appointments_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.role.CreateRoleDTO;
import com.valeop.appointments_api.dto.role.RoleResponseDTO;
import com.valeop.appointments_api.dto.role.UpdateRoleDTO;
import com.valeop.appointments_api.exceptions.BadRequestException;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.RoleMapper;
import com.valeop.appointments_api.model.Role;
import com.valeop.appointments_api.repository.RoleRepository;
import com.valeop.appointments_api.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    static final String MESSAGE = "Role not found with ID #";

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponseDTO> getRoleList() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(RoleMapper::toResponseDTO).toList();
    }

    @Override
    public RoleResponseDTO getRoleById(Integer roleId) {
        Role roleFound = roleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + roleId));

        return RoleMapper.toResponseDTO(roleFound);
    }

    @Override
    public RoleResponseDTO createRole(CreateRoleDTO roleDTO) {
        Role role = RoleMapper.fromCreateRoleDTO(roleDTO);
        return Optional.of(role).filter(r -> !r.getRoleName().isBlank())
                .map(roleRepository::save)
                .map(RoleMapper::toResponseDTO)
                .orElseThrow(() -> new BadRequestException("RoleName should not be empty."));
    }

    @Override
    public RoleResponseDTO updateRole(UpdateRoleDTO roleDTO, Integer roleId) {
        Role roleFound = roleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + roleId));
        RoleMapper.updateFromDTO(roleDTO, roleFound);
        roleRepository.save(roleFound);

        return RoleMapper.toResponseDTO(roleFound);
    }

    @Override
    public RoleResponseDTO deleteRole(Integer roleId) {
        Role roleFound = roleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + roleId));
        roleRepository.deleteById(roleId);
        return RoleMapper.toResponseDTO(roleFound);
    }
}
