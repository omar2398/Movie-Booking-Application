package com.example.MovieBookingApplication.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String theaterName;
    private String theaterLocation;
    private String theaterCapacity;
    private String theaterScreenType;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "theater")
    private List<Show> show;
}
