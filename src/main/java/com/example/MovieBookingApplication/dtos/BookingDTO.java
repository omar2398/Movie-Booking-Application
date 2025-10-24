package com.example.MovieBookingApplication.dtos;

import com.example.MovieBookingApplication.Entities.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {

    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private Double price;
    private BookingStatus bookingStatus;
    private List<String> seatsNumbers;
    private Long userID;
    private Long showID;
}
