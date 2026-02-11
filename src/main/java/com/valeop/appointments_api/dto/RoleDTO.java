package com.valeop.appointments_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleDTO {
    private Integer roleId;

    @NotBlank(message = "Role name should not be empty.")
    @Size(max = 20, min = 2, message = "Role name characters limit exceeded. (20)")
    private String roleName;

    public RoleDTO() {
    }

    public RoleDTO(Integer roleId,
            @NotBlank(message = "Role name should not be empty.") @Size(max = 20, message = "Role name characters limit exceeded. (20)") String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
