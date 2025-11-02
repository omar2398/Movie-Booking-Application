package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.Entities.User;
import com.example.MovieBookingApplication.Repositories.UserRepository;
import com.example.MovieBookingApplication.dtos.LoginRequestDTO;
import com.example.MovieBookingApplication.dtos.LoginResponseDTO;
import com.example.MovieBookingApplication.dtos.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public User registerNormalUser(RegisterRequestDTO registerRequestDTO){
        if (userRepository.findUserByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("User already registered!");
        }
        User user = new User();
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO){
        if (userRepository.findUserByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("Admin already registered!");
        }
        User user = new User();
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findUserByUsername(loginRequestDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("There is no user found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );
        final String jwtToken = jwtService.generateToken(user);

        return LoginResponseDTO
                .builder()
                .jwtToken(jwtToken)
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
