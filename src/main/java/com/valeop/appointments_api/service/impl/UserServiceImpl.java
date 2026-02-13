package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.user.CreateUserDTO;
import com.valeop.appointments_api.dto.user.UpdatePasswordDTO;
import com.valeop.appointments_api.dto.user.UpdateUserDTO;
import com.valeop.appointments_api.dto.user.UserResponseDTO;
import com.valeop.appointments_api.exceptions.BadRequestException;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.UserMapper;
import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.Role;
import com.valeop.appointments_api.model.User;
import com.valeop.appointments_api.repository.PersonRepository;
import com.valeop.appointments_api.repository.RoleRepository;
import com.valeop.appointments_api.repository.UserRepository;
import com.valeop.appointments_api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
        private final UserRepository userRepository;
        private final PersonRepository personRepository;
        private final RoleRepository roleRepository;
        private static final String MESSAGE = "User not found with ID #";

        @Autowired
        public UserServiceImpl(UserRepository userRepository, PersonRepository personRepository,
                        RoleRepository roleRepository) {
                this.userRepository = userRepository;
                this.personRepository = personRepository;
                this.roleRepository = roleRepository;
        }

        @Override
        public List<UserResponseDTO> getUsersList() {
                List<User> userList = userRepository.findAll();
                return userList.stream().map(UserMapper::toResponseDTO).toList();
        }

        @Override
        public UserResponseDTO getUserById(Integer userId) {
                User userFound = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + userId));
                return UserMapper.toResponseDTO(userFound);
        }

        @Override
        public UserResponseDTO createUser(CreateUserDTO userDTO) {
                Person personFound = personRepository.findByPersonId(userDTO.person().getPersonId())
                                .orElseThrow(() -> new ResourceNotFoundException("Person does not exist."));

                Role roleFound = roleRepository.findByRoleId(userDTO.role().getRoleId())
                                .orElseThrow(() -> new ResourceNotFoundException("Role does not exist."));

                User newUser = UserMapper.fromCreateUserDTO(userDTO, personFound, roleFound);

                return UserMapper.toResponseDTO(userRepository.save(newUser));
        }

        @Override
        public UserResponseDTO updateUser(UpdateUserDTO userDTO, Integer userId) {
                User userFound = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + userId));

                if (userDTO.person() != null) {
                        Integer personId = userDTO.person().getPersonId();
                        if (!personRepository.existsById(personId)) {
                                throw new ResourceNotFoundException("Person does not exist with ID #" + userId);
                        }
                        Person personFound = personRepository.getReferenceById(userId);
                        userFound.setPerson(personFound);
                }

                if (userDTO.role() != null) {
                        Integer roleId = userDTO.role().getRoleId();
                        if (!roleRepository.existsById(roleId)) {
                                throw new ResourceNotFoundException("Role does not exist with ID #" + roleId);
                        }
                        Role roleFound = roleRepository.getReferenceById(roleId);
                        userFound.setRole(roleFound);
                }

                UserMapper.updateFromDTO(userDTO, userFound);

                User userSaved = userRepository.save(userFound);
                return UserMapper.toResponseDTO(userSaved);
        }

        @Override
        public UserResponseDTO updateUserPassword(UpdatePasswordDTO userDTO, Integer userId) {
                User user = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User does not exist with ID #" + userId));
                if (!user.getPasswordHash().matches(userDTO.currentPassword())) {
                        throw new BadRequestException("Invalid current password");
                }
                if (!userDTO.newPassword().equals(userDTO.confirmNewPassword())) {
                        throw new BadRequestException("New password does not match with password confirmed");
                }
                user.setPasswordHash(userDTO.newPassword());
                userRepository.save(user);
                return UserMapper.toResponseDTO(user);
        }

        @Override
        public UserResponseDTO deleteUser(Integer userId) {
                User userFound = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + userId));
                userRepository.deleteById(userId);
                return UserMapper.toResponseDTO(userFound);
        }
}
