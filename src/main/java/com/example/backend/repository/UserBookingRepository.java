package com.example.backend.repository;

import com.example.backend.model.Bookings.UserBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserBookingRepository extends JpaRepository<UserBooking, Long> {

    List<UserBooking> findByUserId(long id);
    List<UserBooking> findByProjectId(long id);

}
