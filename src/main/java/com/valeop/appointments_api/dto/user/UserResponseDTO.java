package com.valeop.appointments_api.dto.user;

import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.Role;

public record UserResponseDTO(
                Integer userId,
                Person person,
                Role role,
                String email) {
}
