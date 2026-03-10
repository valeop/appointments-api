package com.valeop.appointments_api.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @Email(message = "Email should be well structured.") @NotBlank(message = "Email should not be empty.") @Size(max = 40, message = "Email characters limit exceeded. (40)") String email,
        @NotBlank(message = "Password should not be empty.") @Size(min = 8, message = "Minimun password characters is 8.") String password) {
}
