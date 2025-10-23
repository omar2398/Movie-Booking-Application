package com.example.MovieBookingApplication.Repositories;

import com.example.MovieBookingApplication.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    /*
    I should do 7 APIs
    get All and get by gene, language, and title,
    and add, update, and delete
    4 + 3 = 7
     */
    Optional<List<Movie>> findMoviesByGenre(String genre);
    Optional<List<Movie>> findMoviesByLanguage(String language);
    Optional<List<Movie>> findMoviesByName(String name);



}
