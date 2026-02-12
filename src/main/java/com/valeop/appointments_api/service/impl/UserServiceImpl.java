package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.user.CreateUserDTO;
import com.valeop.appointments_api.dto.user.UpdateUserDTO;
import com.valeop.appointments_api.dto.user.UserResponseDTO;
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

                if (userDTO.person().getPersonId() != null) {
                        Person personFound = personRepository.findByPersonId(userDTO.person().getPersonId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Person does not exist."));
                        userFound.setPerson(personFound);
                }

                if (userDTO.role().getRoleId() != null) {
                        Role roleFound = roleRepository.findByRoleId(userDTO.role().getRoleId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Role does not exist."));
                        userFound.setRole(roleFound);
                }

                UserMapper.updateFromDTO(userDTO, userFound);

                User userSaved = userRepository.save(userFound);
                return UserMapper.toResponseDTO(userSaved);
        }

        @Override
        public UserResponseDTO deleteUser(Integer userId) {
                User userFound = userRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + userId));
                userRepository.deleteById(userId);
                return UserMapper.toResponseDTO(userFound);
        }
}
