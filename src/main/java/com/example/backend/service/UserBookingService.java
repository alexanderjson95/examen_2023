package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.model.Bookings.BookingRequest;
import com.example.backend.model.Bookings.Bookings;
import com.example.backend.model.Bookings.PatchBookingRequest;
import com.example.backend.model.Bookings.UserBooking;
import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.ProjectRequest;
import com.example.backend.model.Projects.UserProject;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserBookingRepository;
import com.example.backend.repository.UserProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserBookingService  {

    @Autowired
    UserBookingRepository repo;
    @Autowired
    BookingRepository bRepo;
    private final ProjectService projectService;
    private final UserService userService;

    public List<UserBooking> getBookingsByUser(long userId){
        return repo.findByUser_Id(userId);
    }

    public Bookings getById(Long bookingId) {
        return  bRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public UserBooking getByUserBookingByBookingId(long bookingId) {
        return  repo.findByBooking_Id(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public void updateBooking(Long bookingId, PatchBookingRequest req){
        // Skapar projektet
        Bookings booking = getById(bookingId);
        UserBooking ub = getByUserBookingByBookingId(bookingId);
        System.out.println("Booking ID result: " + ub.getUser().getUsername());
        if (req.getStartHour() != null ) booking.setStartHour(req.getStartHour());
        if (req.getStartMinute() != null ) booking.setStartMinute(req.getStartMinute());
        if (req.getEndHour() != null ) booking.setEndHour(req.getEndHour());
        if (req.getEndMinute() != null ) booking.setEndMinute(req.getEndMinute());
        if (req.isAccepted()) {
            ub.setAccepted(true);
            ub.setAvailability(false);
        };
        bRepo.save(booking);
        repo.save(ub);
    }

    public void createBooking(BookingRequest req, Long userId){
        // Skapar projektet
        Bookings booking = new Bookings();
        booking.setStartHour(req.getStartHour());
        booking.setStartMinute(req.getStartMinute());
        booking.setEndHour(req.getEndHour());
        booking.setEndMinute(req.getEndMinute());
        booking.setDateMillis(req.getDateMillis());
        bRepo.save(booking);

        Users user = userService.findUserById(userId);
        Project project = projectService.findProjectById(req.getProjectId());
        if(repo.existsByUser_IdAndBooking_DateMillisAndAvailabilityFalse(userId,req.getDateMillis())){
            throw new DataTakenException("Användarnamnet är redan bokad denna dag!");
        };
        UserBooking userBooking = new UserBooking();
        userBooking.setUser(user);
        userBooking.setProject(project);
        userBooking.setAccepted(false); // En förfrågan ska alltid vara false och ändras till true med Patch istället
        userBooking.setAvailability(req.isAvailability());
        userBooking.setBooking(booking);
        repo.save(userBooking);
    }



    public List<UserBooking> getBookingsByProject(long projectId){
        return repo.findByProject_Id(projectId);
    }

    public List<UserBooking> getBookingsByProjectUser(long projectId, long userId){
        return repo.findByProject_Id(projectId);
    }

    public UserBooking acceptBooking(Long bookingId, Long userId, Boolean response){
        UserBooking ub = repo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Ingen bokning hittad"));
        ub.setAccepted(response);
        return repo.save(ub);
    }

    public void removeBooking(long userId){
        repo.deleteById(userId);
    }

}
