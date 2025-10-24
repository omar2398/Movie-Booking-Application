package com.example.MovieBookingApplication.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.beans.FeatureDescriptor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime showTime;
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "show")
    private List<Booking> booking;
}
