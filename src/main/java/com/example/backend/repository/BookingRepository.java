package com.example.backend.repository;

import com.example.backend.model.Project;
import org.apache.logging.log4j.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projectplatform.backend.model.Bookings;
import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Bookings, Integer> {

    Optional<Bookings> findByUserId(long id);
    Optional<Bookings> findByProjectId(long id);



}
