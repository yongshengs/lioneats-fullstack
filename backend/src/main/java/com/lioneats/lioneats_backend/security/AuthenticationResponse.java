package com.lioneats.lioneats_backend.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private String username;
    private String email;
    private String message;
}