package com.example.backend.repository;

import com.example.backend.model.Bookings.UserBooking;
import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserBookingRepository extends JpaRepository<UserBooking, Long> {

    List<UserBooking> findByUser_Id(Long userId);

    @EntityGraph(attributePaths = {"user", "project"})
    List<UserBooking> findByProject_Id(Long id);
    @EntityGraph(attributePaths = {"user", "project", "booking"})
    List<UserBooking> findByUser_IdAndProject_Id(Long userId, Long projectId);

    @EntityGraph(attributePaths = {"user", "project", "booking"})
    List<UserBooking> findByUser_IdAndProject_IdAndAccepted(Long userId, Long projectId, Boolean accepted);
    Optional<UserBooking> findByBooking_Id(Long bookingId);

    boolean existsByUser_IdAndBooking_DateMillisAndAvailabilityFalse(Long userId, Long dateMillis);
}
