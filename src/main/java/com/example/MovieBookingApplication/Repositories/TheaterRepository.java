package com.example.MovieBookingApplication.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MovieBookingApplication.Entities.Theater;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    List<Theater> findTheaterByTheaterLocation(String theaterLocation);
}
