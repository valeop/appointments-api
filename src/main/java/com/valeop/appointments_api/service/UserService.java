package com.valeop.appointments_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.user.CreateUserDTO;
import com.valeop.appointments_api.dto.user.UpdatePasswordDTO;
import com.valeop.appointments_api.dto.user.UpdateUserDTO;
import com.valeop.appointments_api.dto.user.UserResponseDTO;

@Service
public interface UserService {
    List<UserResponseDTO> getUsersList();

    UserResponseDTO getUserById(Integer userId);

    UserResponseDTO createUser(CreateUserDTO userDTO);

    UserResponseDTO updateUser(UpdateUserDTO userDTO, Integer userId);

    UserResponseDTO updateUserPassword(UpdatePasswordDTO userDTO, Integer userId);

    UserResponseDTO deleteUser(Integer userId);
}
