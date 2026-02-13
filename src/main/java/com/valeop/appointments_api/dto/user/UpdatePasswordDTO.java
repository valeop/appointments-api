package com.valeop.appointments_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDTO(
        @NotBlank(message = "Current password must not be empty.") String currentPassword,
        @NotBlank(message = "New password must not be empty.") @Size(min = 8, message = "New Password should have at least 8 characters") String newPassword,
        @NotBlank(message = "Validation's password must not be empty.") String confirmNewPassword) {
}
