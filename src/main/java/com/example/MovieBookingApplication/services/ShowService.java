package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.Entities.Booking;
import com.example.MovieBookingApplication.Entities.Show;
import com.example.MovieBookingApplication.Repositories.MovieRepository;
import com.example.MovieBookingApplication.Repositories.ShowRepository;
import com.example.MovieBookingApplication.Repositories.TheaterRepository;
import com.example.MovieBookingApplication.dtos.ShowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;

    public List<Show> getAllShows(){
        return showRepository.findAll();
    }
    public List<Show> getShowsByTheaterId(Long id){
        return showRepository.findShowsByTheaterId(id).
                orElseThrow(()-> new RuntimeException("There is no shows for this theater id: " + id));
    }

    public List<Show> getShowsByMovieId(Long id){
        return showRepository.findShowsByMovieId(id).
                orElseThrow(()-> new RuntimeException("There is no shows for this movie id : " + id));
    }

    public Show addShow(ShowDTO showDTO){
        Show show = new Show();
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movieRepository.findById(showDTO.getMovieId()).
                orElseThrow(()-> new RuntimeException("There is no Movie with this id: " + showDTO.getMovieId())));
        show.setTheater(theaterRepository.findById(showDTO.getTheaterId()).
                orElseThrow(()-> new RuntimeException("There is no Theater with this id: " + showDTO.getTheaterId())));

        return showRepository.save(show);
    }

    public Show updateShow(Long id, ShowDTO showDTO){

        Show show = showRepository.findById(id).orElseThrow(()-> new RuntimeException("There is no show with this id : " + id));

        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movieRepository.findById(showDTO.getMovieId()).
                orElseThrow(()-> new RuntimeException("There is no Movie with this id: " + showDTO.getMovieId())));
        show.setTheater(theaterRepository.findById(showDTO.getTheaterId()).
                orElseThrow(()-> new RuntimeException("There is no Theater with this id: " + showDTO.getTheaterId())));

        return showRepository.save(show);
    }

    public void deleteShow(Long id){
        Show show = showRepository.findById(id).orElseThrow(()-> new RuntimeException("There is no show with this id : " + id));
            List<Booking> bookings = show.getBooking();
            if (!bookings.isEmpty()){
                throw new RuntimeException("Can't delete show with existing booking!");
            }else{
                showRepository.deleteById(id);
            }

    }



}
