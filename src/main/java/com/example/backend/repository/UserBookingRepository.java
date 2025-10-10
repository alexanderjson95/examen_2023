package com.example.backend.repository;

import com.example.backend.model.Bookings.BookingStatusType;
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

    void deleteByUser_Id(Long userId);
    void deleteByUser_IdAndBooking_Id(Long userId, Long bookingId);


    int countByBooking_Id(Long bookingId);

    List<UserBooking> findAllByBooking_Id(Long bookingId);
    Optional<UserBooking> findByBooking_IdAndUser_Id(Long bookingId, Long userId);
    boolean existsByUser_IdAndBooking_DateMillis(Long userId, Long dateMillis);
    // Helper för att kolla så användare inte redan är bokad på dagen
    boolean existsByUser_IdAndBooking_DateMillisAndStatus(Long userId, Long dateMillis, BookingStatusType status);
    boolean existsByUser_IdAndBooking_DateMillisAndStatusIn(Long userId, Long dateMillis, List<BookingStatusType>status);

    List<UserBooking> findAllByBooking_IdAndStatus(Long bookingId,  BookingStatusType status);

}
