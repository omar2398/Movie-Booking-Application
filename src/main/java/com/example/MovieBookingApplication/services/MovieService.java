package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.Entities.Movie;
import com.example.MovieBookingApplication.Repositories.MovieRepository;
import com.example.MovieBookingApplication.dtos.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setLanguage(movieDTO.getLanguage());

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre){
        Optional<List<Movie>> listOfMovieBox = movieRepository.findMoviesByGenre(genre);
        if (listOfMovieBox.isPresent()){
            return listOfMovieBox.get();
        }else throw new RuntimeException("There is no movies for the genre: " + genre);
    }

    public List<Movie> getMoviesByLanguage(String language){
        Optional<List<Movie>> listOfMovieBox = movieRepository.findMoviesByLanguage(language);
        if (listOfMovieBox.isPresent()){
            return listOfMovieBox.get();
        }else throw new RuntimeException("There is no movies for the language: " + language);
    }

    public List<Movie> getMoviesByName(String name){
        Optional<List<Movie>> listOfMovieBox = movieRepository.findMoviesByName(name);
        if (listOfMovieBox.isPresent()){
            return listOfMovieBox.get();
        }else throw new RuntimeException("There is no movies for the title: " + name);
    }

    public Movie updateMovie(Long id, MovieDTO movieDTO){
        Movie movie = new Movie();
            movie = movieRepository.findById(id).orElseThrow(()-> new RuntimeException("There is no movie with this id: " + id));

        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setLanguage(movieDTO.getLanguage());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id){
        if(!movieRepository.existsById(id)){
            throw new RuntimeException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }
}
