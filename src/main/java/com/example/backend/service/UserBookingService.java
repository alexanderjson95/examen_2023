package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.Exceptions.NotAdminException;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.model.Bookings.*;
import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.RequestType;
import com.example.backend.model.Projects.UserProjectResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sound.midi.Patch;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserBookingService {

    private final UserBookingRepository repo;
    private final BookingRepository bRepo;
    private final UserService uService;
    private final ProjectService projectService;


    private static final String nullErrorMessageBooking = "Bokningen kunde inte hittas! ";
    private static final String nullErrorMessageUserBooking = "Användaren kunde inte hittas i bokningen! ";
    private static final String noAccessGrantetMessageBooking = "Du har inte behörighet att ändra bokningen! ";


    public List<UserBooking> getBookingsByUser(long userId) {
        List<UserBooking> bookings = repo.findByUser_Id(userId);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }


    public UserBooking getUserBookingById(Long bookingId, Long userId) {

        return repo.findByBooking_IdAndUser_Id(bookingId,userId)
                .orElseThrow(() -> new ResourceNotFoundException("FROM USERBOOKING!!!!!!!! getUserBookingById"));
    }


    public Bookings getById(Long bookingId) {
        return bRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(nullErrorMessageBooking));
    }

    public List<UserBooking> getUserBookingsByProject(long projectId) {
        List<UserBooking> bookings = repo.findByProject_Id(projectId);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }


    public void removeBooking(long bookingId, long self) {
        UserBooking ub = getUserBookingById(bookingId,self);
        userBookingAdminHelper(ub);
        bRepo.deleteById(bookingId);
    }

    public void removeUserBooking(long bookingId,long userId) {
        UserBooking ub = getUserBookingById(bookingId,userId);
        String status = ub.getStatus().toString();
        int participantsLeft = repo.countByBooking_Id(bookingId);
        if (participantsLeft <= 1 || status.equals("ADMIN")) {
            bRepo.deleteById(bookingId); // kör ej service metoden här då detta är en auto funktion och ej kräver beöhrighet
        } else {
            repo.deleteById(ub.getId());
        }
    }


    public Bookings updateBooking(Long bookingId, PatchBookingRequest req, Long self){
        // Skapar projektet
        Bookings booking = getById(bookingId);
        UserBooking ub = getUserBookingById(bookingId,self);
        userBookingAdminHelper(ub);
        if (req.getStartHour() != null ) booking.setStartHour(req.getStartHour());
        if (req.getStartMinute() != null ) booking.setStartMinute(req.getStartMinute());
        if (req.getEndHour() != null ) booking.setEndHour(req.getEndHour());
        if (req.getEndMinute() != null ) booking.setEndMinute(req.getEndMinute());
        if (req.getBookingTitle() != null) booking.setBookingTitle(req.getBookingTitle());
        if (req.getBookingDescription() != null) booking.setBookingDescription(req.getBookingDescription());
        return bRepo.save(booking);
    }



    public Bookings createBooking(BookingRequest req, Long self){
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
            if (user.getId() == self){
                userBooking.setStatus(BookingStatusType.ADMIN);
            } else {
                userBooking.setStatus(BookingStatusType.INVITE); // skickar inbjudan till lista av användare
            }
            userBooking.setBooking(booking);
            // Eftersom vi användar CascadeType.All, läggs userbooking entity  också in i SQL commandot
            booking.getUserBookings().add(userBooking);
        }

        return bRepo.save(booking);
    }

    // Ändrar BookingsResponse
    public UserBooking updateUserBooking(Long bookingId, Long userId, String response){
        UserBooking ub = repo.findByBooking_IdAndUser_Id(bookingId,userId)
                .orElseThrow(() -> new ResourceNotFoundException(nullErrorMessageBooking));
        ub.setStatus(BookingStatusType.valueOf(response.toUpperCase()));
         return repo.save(ub);
    }



    // HELPERS


    public void userBookingAdminHelper(UserBooking ub) {
        String admin = "ADMIN";
        boolean isAdmin = admin.equals(ub.getStatus().toString());
        if (!isAdmin) {
            throw new NotAdminException("NOOO ACCESS");
        }
    }

    // ej exceptino här, detta är endast en check





    public List<UserProjectResponse> getAvailable(Long projectId, Long dateMillis) {
        List<UserProjectResponse> members = projectService.findUserProjectsByRequestType(RequestType.ACCEPTED.toString(), projectId);
        return members.stream()
                .filter(u -> !repo.existsByUser_IdAndBooking_DateMillis(u.getUserId(),dateMillis))
                .toList();
    }

    // Vid bokningsförfrågan till användare måste följande kriterier uppfyllas:
    // A) Användaren har en bokning aktiv - men den är satt på available
    // B) Användaren har ingen bokning aktiv

    public boolean canUserWork(Long userId, Long dateMillis) {
        boolean hasAnyBooking = repo.existsByUser_IdAndBooking_DateMillis(userId, dateMillis);
        if (!hasAnyBooking) {
            return true;
        } else throw new DataTakenException("Användaren är redan bokad denna dag!");
    }

    // Oanvända med bra i framtid:

    public List<UserBooking> getAllUsersOnBooking(long bookingId, BookingStatusType status) {
        List<UserBooking> bookings = repo.findAllByBooking_IdAndStatus(bookingId, status);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }
    public List<Bookings> getByIds(List<Long> bookingIds) {
        List<Bookings> bookings = bRepo.findByIdIn(bookingIds);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException(nullErrorMessageUserBooking);
        }
        return bookings;
    }

}
