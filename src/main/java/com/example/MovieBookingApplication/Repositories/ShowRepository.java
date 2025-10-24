package com.example.MovieBookingApplication.Repositories;

import com.example.MovieBookingApplication.Entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Optional<List<Show>> findShowsByTheaterId(Long id);
    Optional<List<Show>> findShowsByMovieId(Long id);
}
