package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.model.Bookings.Bookings;
import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {


}
