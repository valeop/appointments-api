package com.valeop.appointments_api.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.user.UpdatePasswordDTO;
import com.valeop.appointments_api.dto.user.UserResponseDTO;
import com.valeop.appointments_api.exceptions.BadRequestException;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.jwt.JwtService;
import com.valeop.appointments_api.mapper.UserMapper;
import com.valeop.appointments_api.model.User;
import com.valeop.appointments_api.model.UserSecurityDetails;
import com.valeop.appointments_api.repository.UserRepository;

import io.jsonwebtoken.Claims;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));

        User user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + authRequest.email()));

        UserSecurityDetails userDetails = new UserSecurityDetails(user);
        String token = jwtService.generateAccessToken(userDetails);

        return new AuthResponse(token, "Bearer", jwtService.extractClaim(token, Claims::getExpiration),
                userDetails.getUsername(),
                userDetails.getAuthorities().toString());
    }

    public UserResponseDTO updatePassword(UpdatePasswordDTO updateDTO, Integer userId) {
        User userFound = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User does not exist with ID #" + userId));

        UserSecurityDetails userDetails = new UserSecurityDetails(userFound);

        if (!passwordEncoder.matches(updateDTO.currentPassword(), userDetails.getPassword())) {
            throw new BadRequestException("Invalid current password");
        }
        if (!updateDTO.newPassword().equals(updateDTO.confirmNewPassword())) {
            throw new BadRequestException("New password does not match with password confirmed");
        }
        if (passwordEncoder.matches(updateDTO.newPassword(), userDetails.getPassword())) {
            throw new BadRequestException("New password shouldn't be the same as current one");
        }

        userFound.setPasswordHash(passwordEncoder.encode(updateDTO.newPassword()));
        User userUpdated = userRepository.save(userFound);
        return UserMapper.toResponseDTO(userUpdated);
    }
}