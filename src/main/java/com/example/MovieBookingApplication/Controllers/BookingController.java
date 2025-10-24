package com.example.MovieBookingApplication.Controllers;

import com.example.MovieBookingApplication.Entities.Booking;
import com.example.MovieBookingApplication.Entities.BookingStatus;
import com.example.MovieBookingApplication.dtos.BookingDTO;
import com.example.MovieBookingApplication.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAllBookings")
    public ResponseEntity<List<Booking>> getAllBooking(){
        return ResponseEntity.ok(bookingService.getAllBooking());
    }

    @GetMapping("/getUserBooking/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId){
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @GetMapping("/getShowBooking/{showId}")
    public ResponseEntity<List<Booking>> getShowBookings(@PathVariable Long showId){
        return ResponseEntity.ok(bookingService.getShowBookings(showId));
    }

    @PostMapping("/addBooking")
    public ResponseEntity<Booking> addBooking(@RequestBody BookingDTO bookingDTO){
        return ResponseEntity.ok(bookingService.addBooking(bookingDTO));
    }

    @PutMapping("/updateBooking/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id,@RequestBody BookingDTO bookingDTO){
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateShowStatus/{id}")
    public ResponseEntity<Booking> updateShowStatus(@PathVariable Long id,@RequestParam BookingStatus bookingStatus){
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, bookingStatus));
    }

    @GetMapping("/getBookingsByStatus/{bookingStatus}")
    public ResponseEntity<List<Booking>> getShowingsByStatus(@PathVariable BookingStatus bookingStatus){
        return ResponseEntity.ok(bookingService.getBookingsByStatus(bookingStatus));
    }
}
