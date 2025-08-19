package com.example.backend.service;

import com.example.backend.model.Bookings.UserBooking;
import com.example.backend.repository.UserBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookingService  {

    @Autowired
    UserBookingRepository repo;

    public List<UserBooking> getBookingsByUser(long userId){
        return repo.findByUserId(userId);
    }

    public List<UserBooking> getBookingsByProject(long projectId){
        return repo.findByProjectId(projectId);
    }

    public UserBooking addBooking(UserBooking userBooking){
        return repo.save(userBooking);
    }

    public void removeBooking(long userId){
        repo.deleteById(userId);
    }
//
//    public UserBooking updateBooking(UserBooking userBooking){
//        repo.save(userBooking);
//    }

}
