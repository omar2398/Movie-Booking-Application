package com.example.MovieBookingApplication.Controllers;

import com.example.MovieBookingApplication.Entities.User;
import com.example.MovieBookingApplication.dtos.LoginRequestDTO;
import com.example.MovieBookingApplication.dtos.LoginResponseDTO;
import com.example.MovieBookingApplication.dtos.RegisterRequestDTO;
import com.example.MovieBookingApplication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registerAdminUser")
    public ResponseEntity<User> registerAdminUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        return ResponseEntity.ok(authenticationService.registerAdminUser(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }

}
