package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.Entities.Booking;
import com.example.MovieBookingApplication.Entities.BookingStatus;
import com.example.MovieBookingApplication.Entities.Show;
import com.example.MovieBookingApplication.Repositories.BookingRepository;
import com.example.MovieBookingApplication.Repositories.ShowRepository;
import com.example.MovieBookingApplication.Repositories.UserRepository;
import com.example.MovieBookingApplication.dtos.BookingDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowRepository showRepository;

    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    public List<Booking> getUserBookings(Long userId){
        return bookingRepository.findBookingsByUserId(userId).
                orElseThrow(()-> new RuntimeException("There is no bookings for this user id: " + userId));
    }

    public List<Booking> getShowBookings(Long showId){
        return bookingRepository.findBookingsByShowId(showId).
                orElseThrow(()-> new RuntimeException("There is no bookings for this shows id: " + showId));
    }

    public Booking addBooking(BookingDTO bookingDTO){
        Show show = showRepository.findById(bookingDTO.getShowID()).
                orElseThrow(() -> new RuntimeException("Not show found"));
        if (bookingDTO.getNumberOfSeats() != bookingDTO.getSeatsNumbers().size()){
            throw new RuntimeException("Number of seats and seat numbers must be equal!");
        }

        if (!isThereAnyAvailableSeats(show, bookingDTO.getNumberOfSeats())){
            throw new RuntimeException("There are no available seats");
        }

        validateDuplicationOfSeats(show, bookingDTO.getSeatsNumbers());

        Booking booking = new Booking();

        booking.setBookingStatus(bookingDTO.getBookingStatus());
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatsNumbers(bookingDTO.getSeatsNumbers());
        booking.setPrice(bookingDTO.getPrice());
        booking.setBookingTime(bookingDTO.getBookingTime());
        booking.setUser(userRepository.findById(bookingDTO.getUserID())
                .orElseThrow(()-> new RuntimeException("There is no user with id : " + bookingDTO.getUserID())));
        booking.setShow(showRepository.findById(bookingDTO.getShowID())
                .orElseThrow(()-> new RuntimeException("There is no show with id : " + bookingDTO.getShowID())));

        return bookingRepository.save(booking);
    }

    public boolean isThereAnyAvailableSeats(Show show, Integer numberOfSeats){
        int bookedSeats = show.getBooking()
                .stream()
                .filter(book -> book.getBookingStatus() != BookingStatus.CANCELED)
                .mapToInt(Booking :: getNumberOfSeats)
                .sum();

        return (show.getTheater().getTheaterCapacity() - bookedSeats) >= numberOfSeats;
    }

    public void validateDuplicationOfSeats(Show show, List<String> bookingSeatNumbers){
        Set<String> alreadyOccupied = show.getBooking()
                .stream()
                .filter(b -> b.getBookingStatus() != BookingStatus.CANCELED)
                .flatMap(b -> b.getSeatsNumbers().stream())
                .collect(Collectors.toSet());

        List<String> duplications = bookingSeatNumbers.
                stream().
                filter(alreadyOccupied :: contains)
                .collect(Collectors.toList());
        if(!duplications.isEmpty()){
            throw new RuntimeException("Can't chose selected seats");
        }

    }

    public Booking updateBooking(Long id, BookingDTO bookingDTO){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("There is no booking with id : " + id));

        booking.setBookingStatus(bookingDTO.getBookingStatus());
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatsNumbers(bookingDTO.getSeatsNumbers());
        booking.setPrice(bookingDTO.getPrice());
        booking.setBookingTime(bookingDTO.getBookingTime());
        booking.setUser(userRepository.findById(bookingDTO.getUserID())
                .orElseThrow(()-> new RuntimeException("There is no user with id : " + bookingDTO.getUserID())));
        booking.setShow(showRepository.findById(bookingDTO.getShowID())
                .orElseThrow(()-> new RuntimeException("There is no show with id : " + bookingDTO.getShowID())));

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id){
        if(bookingRepository.existsById(id)){
            bookingRepository.deleteById(id);
        }else throw new RuntimeException("There is no booking with id : " + id);
    }

    @Transactional
    public Booking updateBookingStatus(Long id, BookingStatus bookingStatus){
        return bookingRepository.updateBookingStatus(id, bookingStatus).
                orElseThrow(()-> new RuntimeException("There is no show for this id"));
    }

    public List<Booking> getBookingsByStatus(BookingStatus bookingStatus){
        return bookingRepository.findBookingsByBookingStatus(bookingStatus).
                orElseThrow(()-> new RuntimeException("There is no bookings for this statue: " + bookingStatus.toString()));
    }
}
