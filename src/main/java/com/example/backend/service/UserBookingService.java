package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.model.Bookings.*;
import com.example.backend.model.Projects.Project;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserBookingService  {

    private final UserBookingRepository repo;
    private final BookingRepository bRepo;
    private final UserService uService;
    private final ProjectService projectService;



    private static final String nullErrorMessageBooking = "Bokningen kunde inte hittas! ";
    private static final String nullErrorMessageUserBooking = "Användaren kunde inte hittas i bokningen! ";


    public List<UserBooking> getBookingsByUser(long userId){
        List<UserBooking> bookings =  repo.findByUser_Id(userId);
        if (bookings.isEmpty()){
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }

    public List<UserBooking> getAllUsersOnBooking(long bookingId, BookingStatusType status){
        List<UserBooking> bookings =  repo.findAllByBooking_IdAndStatus(bookingId,status);
        if (bookings.isEmpty()){
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }

    public Bookings getById(Long bookingId) {
        return  bRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(nullErrorMessageBooking));
    }

    public List<UserBooking> getUserBookingsByBookingId(long bookingId) {
        List<UserBooking> bookings =  repo.findAllByBooking_Id(bookingId);
        if (bookings.isEmpty()){
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }



    public List<UserBooking> getUserBookingsByProject(long projectId){
        List<UserBooking> bookings =  repo.findByProject_Id(projectId);
        if (bookings.isEmpty()){
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }

    public void removeAllUserBookingsFromUser(long userId){
        repo.deleteByUser_Id(userId);
    }

    public void removeUserFromBooking(long userId, long bookingId){
        repo.deleteByUser_IdAndBooking_Id(userId,bookingId);
    }

    public void removeUserBooking(long id){
        repo.deleteById(id);
    }

    // Vid bokningsförfrågan till användare måste följande kriterier uppfyllas:
    // A) Användaren har en bokning aktiv - men den är satt på available
    // B) Användaren har ingen bokning aktiv

    public boolean canUserWork(Long userId, Long dateMillis){
        boolean isAvailable = repo.existsByUser_IdAndBooking_DateMillisAndStatus(userId, dateMillis, BookingStatusType.AVAILABLE);
        boolean hasAnyBooking = repo.existsByUser_IdAndBooking_DateMillis(userId,dateMillis);
        if (!hasAnyBooking || isAvailable) {
            return true;
        }
        else throw new DataTakenException("Användaren är redan bokad denna dag!");
    }





    public Bookings updateBooking(Long bookingId, PatchBookingRequest req){
        // Skapar projektet
        Bookings booking = getById(bookingId);
        if (req.getStartHour() != null ) booking.setStartHour(req.getStartHour());
        if (req.getStartMinute() != null ) booking.setStartMinute(req.getStartMinute());
        if (req.getEndHour() != null ) booking.setEndHour(req.getEndHour());
        if (req.getEndMinute() != null ) booking.setEndMinute(req.getEndMinute());
        if (req.getBookingTitle() != null) booking.setBookingTitle(req.getBookingTitle());
        if (req.getBookingDescription() != null) booking.setBookingDescription(req.getBookingDescription());
        return bRepo.save(booking);
    }

    public Bookings createBooking(BookingRequest req){
        // Skapar projektet
        Bookings booking = new Bookings();
        booking.setStartHour(req.getStartHour());
        booking.setStartMinute(req.getStartMinute());
        booking.setEndHour(req.getEndHour());
        booking.setEndMinute(req.getEndMinute());
        booking.setDateMillis(req.getDateMillis());
        List<Users> users = uService.findAllByIdIn(req.getUserIds());
        Project project = projectService.findProjectById(req.getProjectId());
        // Tar varje användare och lägger till på bokningen
        for (Users user : users) {
            // använder vår funktioner för att kasta error om user
            // har en tid på denna dagen som inte är available eller requested
            canUserWork(user.getId(),req.getDateMillis());
            UserBooking userBooking = new UserBooking();
            userBooking.setUser(user);
            userBooking.setProject(project);
            userBooking.setStatus(BookingStatusType.INVITE); // skickar inbjudan till lista av användare
            userBooking.setBooking(booking);
            // Eftersom vi användar CascadeType.All, läggs userbooking entity  också in i SQL commandot
            booking.getUserBookings().add(userBooking);
        }

        return bRepo.save(booking);
    }

    // Ändrar BookingsResponse
    public UserBooking updateUserBooking(Long bookingId, Long userId, String response){
        UserBooking ub = repo.findByBooking_IdAndUser_Id(bookingId,userId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingen bokning med id: " + bookingId + " hittad"));
        ub.setStatus(BookingStatusType.valueOf(response.toUpperCase()));
         return repo.save(ub);
    }



}
