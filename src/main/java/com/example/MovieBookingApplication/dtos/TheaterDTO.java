package com.example.MovieBookingApplication.dtos;

import lombok.Data;

@Data
public class TheaterDTO {
    private String theaterName;
    private String theaterLocation;
    private Integer theaterCapacity;
    private String theaterScreenType;
}
