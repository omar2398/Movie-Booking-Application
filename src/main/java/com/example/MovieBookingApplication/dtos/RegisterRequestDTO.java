package com.example.MovieBookingApplication.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
}
