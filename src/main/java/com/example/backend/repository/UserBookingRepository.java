package com.example.backend.repository;

import com.example.backend.model.Bookings.UserBooking;
import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserBookingRepository extends JpaRepository<UserBooking, Long> {

    List<UserBooking> findByRecipient_Id(Long recipient);
    List<UserBooking> findBySender_Id(Long sender);

    List<UserBooking> findByProject_Id(long id);

}
