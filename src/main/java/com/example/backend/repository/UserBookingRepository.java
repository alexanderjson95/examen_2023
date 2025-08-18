package com.example.backend.repository;

import com.example.backend.model.UserBooking;
import com.example.backend.model.UserProject;
import com.projectplatform.backend.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserBookingRepository extends JpaRepository<UserBooking, Long> {

    List<UserBooking> findByUserId(long id);
    List<UserBooking> findByProjectId(long id);

}
