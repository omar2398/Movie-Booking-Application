package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.Entities.Theater;
import com.example.MovieBookingApplication.Repositories.TheaterRepository;
import com.example.MovieBookingApplication.dtos.TheaterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    public List<Theater> getTheaterByLocation(String location){
        Optional<List<Theater>> theatersBox = theaterRepository.findTheaterByLocation(location);
        if (theatersBox.isPresent()){
            return theatersBox.get();
        }else throw new RuntimeException("There is no Theaters near to this location: "+ location);
    }

    public Theater addTheater(TheaterDTO theaterDTO){

        Theater theater = new Theater();
        theater.setTheaterName(theaterDTO.getTheaterName());
        theater.setTheaterLocation(theaterDTO.getTheaterLocation());
        theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
        theater.setTheaterScreenType(theaterDTO.getTheaterScreenType());

        return theaterRepository.save(theater);
    }

    public Theater updateTheater(Long id, TheaterDTO theaterDTO){
        Optional<Theater> theaterBox = theaterRepository.findById(id);
        if (!theaterBox.isPresent()){
            throw new RuntimeException("There is no theater with the id : " + id);
        }else {
            Theater theater = theaterBox.get();
            theater.setTheaterName(theaterDTO.getTheaterName());
            theater.setTheaterLocation(theaterDTO.getTheaterLocation());
            theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
            theater.setTheaterScreenType(theaterDTO.getTheaterScreenType());

            return theaterRepository.save(theater);
        }
    }

    public void deleteTheater(Long id){
        Optional<Theater> theaterBox = theaterRepository.findById(id);
        if (!theaterBox.isPresent()){
            throw new RuntimeException("There is no theater with the id : " + id);
        }else {
            theaterRepository.deleteById(id);
        }
    }

}
