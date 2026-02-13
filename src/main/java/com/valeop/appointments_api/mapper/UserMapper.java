package com.valeop.appointments_api.mapper;

import org.springframework.stereotype.Component;

import com.valeop.appointments_api.dto.user.CreateUserDTO;
import com.valeop.appointments_api.dto.user.UpdateUserDTO;
import com.valeop.appointments_api.dto.user.UserResponseDTO;
import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.Role;
import com.valeop.appointments_api.model.User;

@Component
public class UserMapper {
    private UserMapper() {
    }

    public static User fromCreateUserDTO(CreateUserDTO dto, Person person, Role role) {
        User user = new User();
        user.setPerson(person);
        user.setRole(role);
        user.setEmail(dto.email());
        user.setPasswordHash(dto.passwordHash());
        return user;
    }

    public static void updateFromDTO(UpdateUserDTO dto, User user) {
        if (!dto.email().isBlank()) {
            user.setEmail(dto.email());
        }
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getUserId(), user.getPerson(), user.getRole(), user.getEmail());
    }
}
