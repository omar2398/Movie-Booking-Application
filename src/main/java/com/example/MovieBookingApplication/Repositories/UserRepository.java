package com.example.MovieBookingApplication.Repositories;

import com.example.MovieBookingApplication.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
