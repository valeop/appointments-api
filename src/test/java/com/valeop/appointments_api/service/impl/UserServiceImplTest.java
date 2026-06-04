package com.valeop.appointments_api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.valeop.appointments_api.dto.user.CreateUserDTO;
import com.valeop.appointments_api.dto.user.UpdateUserDTO;
import com.valeop.appointments_api.dto.user.UserResponseDTO;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.model.BloodType;
import com.valeop.appointments_api.model.Gender;
import com.valeop.appointments_api.model.Person;
import com.valeop.appointments_api.model.Role;
import com.valeop.appointments_api.model.User;
import com.valeop.appointments_api.repository.PersonRepository;
import com.valeop.appointments_api.repository.RoleRepository;
import com.valeop.appointments_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User dUser;
    private Role dRole;
    private Person dPerson;
    private BloodType dBloodType;
    private Gender dGender;

    @BeforeEach
    void setUp() {

        dGender = new Gender();
        dGender.setGenderId(1);
        dGender.setGenderName("Femenino");

        dBloodType = new BloodType();
        dBloodType.setBloodTypeId(5);
        dBloodType.setBloodTypeName("O+");

        dPerson = new Person();
        dPerson.setPersonId(1);
        dPerson.setIdentityCard("555555");
        dPerson.setFirstName("dummyName");
        dPerson.setLastName("dummyLastname");
        dPerson.setBirthDate(LocalDate.of(2001, 03, 30));
        dPerson.setBloodType(dBloodType);
        dPerson.setGender(dGender);

        dRole = new Role();
        dRole.setRoleId(2);
        dRole.setRoleName("DOCTOR");

        dUser = new User();
        dUser.setUserId(1);
        dUser.setEmail("newdoctor@email.com");
        dUser.setPasswordHash("hashedPassword");
        dUser.setPerson(dPerson);
        dUser.setRole(dRole);

    }

    @Test
    void testCreateUser_WithValidData() {
        // Given
        Integer personId = dPerson.getPersonId();
        Integer roleId = dRole.getRoleId();
        CreateUserDTO createDTO = new CreateUserDTO(dPerson, dRole, "newdoctor@email.com", "secretPassword");
        when(personRepository.findByPersonId(personId)).thenReturn(Optional.of(dPerson));
        when(roleRepository.findByRoleId(roleId)).thenReturn(Optional.of(dRole));

        when(passwordEncoder.encode("secretPassword")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(dUser);

        // When
        UserResponseDTO result = userServiceImpl.createUser(createDTO);

        // Then
        assertNotNull(result);

        verify(personRepository, times(1)).findByPersonId(1);
        verify(roleRepository, times(1)).findByRoleId(2);
        verify(passwordEncoder, times(1)).encode("secretPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser_WithValidData() {
        // Given
        Integer userId = dUser.getUserId();
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dUser));

        // When
        UserResponseDTO result = userServiceImpl.deleteUser(userId);

        // Then
        assertNotNull(result);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_WithNoValidData() {
        // Given
        Integer userId = 2;
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser(userId));
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void testGetUserById_WithValidId() {
        // Given
        Integer userId = 1;
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dUser));

        // When
        UserResponseDTO result = userServiceImpl.getUserById(userId);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetUserById_WithNoValidId() {
        // Given
        Integer userId = 2;
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getUserById(userId));
        verify(userRepository, times(1)).findByUserId(any());
    }

    @Test
    void testGetUsersList() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(dUser));

        // When
        List<UserResponseDTO> result = userServiceImpl.getUsersList();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser_WithValidData() {
        // Given
        Integer userId = dUser.getUserId();
        UpdateUserDTO updateDTO = new UpdateUserDTO(1, dPerson, dRole, "update@email.com");
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dUser));
        when(roleRepository.findByRoleId(2)).thenReturn(Optional.of(dRole));
        when(personRepository.findByPersonId(1)).thenReturn(Optional.of(dPerson));
        when(userRepository.save(any(User.class))).thenReturn(dUser);

        // When
        UserResponseDTO result = userServiceImpl.updateUser(updateDTO, userId);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).findByUserId(userId);
        verify(userRepository, times(1)).save(any(User.class));
        verify(personRepository, times(1)).findByPersonId(anyInt());
        verify(roleRepository, times(1)).findByRoleId(anyInt());
    }

    @Test
    void testUpdateUser_WithNoValidUserId() {
        // Given
        Integer userId = 2;
        UpdateUserDTO updateDTO = new UpdateUserDTO(userId, dPerson, dRole, "update@email.com");
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateUser(updateDTO, userId));
        verify(userRepository, times(1)).findByUserId(any());
    }

    @Test
    void testUpdateUser_WithNoValidPersonId() {
        // Given
        Integer userId = dUser.getUserId();
        Integer wrongPersonId = 2;
        dPerson.setPersonId(wrongPersonId);
        UpdateUserDTO updateDTO = new UpdateUserDTO(userId, dPerson, dRole, "update@email.com");
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dUser));
        when(personRepository.findByPersonId(wrongPersonId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateUser(updateDTO, userId));
        verify(personRepository, times(1)).findByPersonId(wrongPersonId);
    }

    @Test
    void testUpdateUser_WithNoValidRoleId() {
        // Given
        Integer userId = dUser.getUserId();
        Integer personId = dPerson.getPersonId();
        Integer wrongRoleId = 4;
        dRole.setRoleId(wrongRoleId);
        UpdateUserDTO updateDTO = new UpdateUserDTO(userId, dPerson, dRole, "update@email.com");
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(dUser));
        when(personRepository.findByPersonId(personId)).thenReturn(Optional.of(dPerson));
        when(roleRepository.findByRoleId(wrongRoleId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateUser(updateDTO, userId));
        verify(roleRepository, times(1)).findByRoleId(wrongRoleId);
    }
}