package com.valeop.appointments_api.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRoleDTO(
        @NotBlank(message = "Role name should not be empty.") @Size(max = 20, min = 2, message = "Role name characters limit exceeded. (20)") String roleName) {
}
