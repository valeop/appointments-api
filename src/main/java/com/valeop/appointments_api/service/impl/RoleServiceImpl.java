package com.valeop.appointments_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.RoleDTO;
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
    public List<RoleDTO> getRoleList() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(RoleMapper::toDTO).toList();
    }

    @Override
    public RoleDTO getRoleById(Integer roleId) {
        Role roleFound = roleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + roleId));

        return RoleMapper.toDTO(roleFound);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = RoleMapper.toEntity(roleDTO);
        return Optional.of(role).filter(r -> !r.getRoleName().isBlank())
                .map(roleRepository::save)
                .map(RoleMapper::toDTO)
                .orElseThrow(() -> new BadRequestException("RoleName should not be empty."));
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO, Integer roleId) {
        Role role = roleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + roleId));
        role.setRoleName(roleDTO.getRoleName());
        roleRepository.save(role);

        return RoleMapper.toDTO(role);
    }

    @Override
    public RoleDTO deleteRole(Integer roleId) {
        Role roleFound = roleRepository.findByRoleId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + roleId));
        roleRepository.deleteById(roleId);
        return RoleMapper.toDTO(roleFound);
    }
}
