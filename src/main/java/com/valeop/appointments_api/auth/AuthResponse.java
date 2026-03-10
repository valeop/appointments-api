package com.valeop.appointments_api.auth;

import java.util.Date;

public record AuthResponse(
        String token,
        String tokenType,
        Date expireDate,
        String email,
        String role) {
}
