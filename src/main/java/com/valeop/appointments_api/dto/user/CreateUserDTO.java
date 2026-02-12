package com.valeop.appointments_api.dto.user;

import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotNull(message = "Person should not be empty.") Person person,
        @NotNull(message = "Role should not be empty.") Role role,
        @Email(message = "Email should be well structured.") @NotBlank(message = "Email should not be empty.") @Size(max = 40, message = "Email characters limit exceeded. (40)") String email,
        @NotBlank(message = "Password should not be empty.") @Size(max = 255, message = "Password characters limit exceeded. (255)") String passwordHash) {
}
