package com.example.MovieBookingApplication.Repositories;

import com.example.MovieBookingApplication.Entities.Booking;
import com.example.MovieBookingApplication.Entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<List<Booking>> findBookingsByUserId(Long id);
    Optional<List<Booking>> findBookingsByShowId(Long id);

    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :bookingStatus WHERE b.id = :id")
    Optional<Booking> updateBookingStatus(@Param("id") Long id, @Param("bookingStatus") BookingStatus bookingStatus);

    Optional<List<Booking>> findBookingsByBookingStatus(BookingStatus bookingStatus);
}
