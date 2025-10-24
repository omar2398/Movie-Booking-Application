package com.example.MovieBookingApplication.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowDTO {
    private LocalDateTime showTime;
    private double price;
    private Long theaterId;
    private Long movieId;
}
