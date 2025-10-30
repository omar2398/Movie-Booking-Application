package com.example.MovieBookingApplication.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public class JwtAuthenticationFilter {

    @Autowired
    private final AuthenticationManager authenticationManager;
}
