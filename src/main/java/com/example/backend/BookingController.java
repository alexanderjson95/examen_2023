package com.example.backend;

import com.example.backend.model.Bookings.*;
import com.example.backend.model.Projects.UserProjectResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.service.UserBookingService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@ToExport("controller")
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final UserBookingService service;
    private final UserService userService;


    @PostMapping()
    public ResponseEntity<List<BookingResponse>> createBooking(@RequestBody BookingRequest req,Principal principal){
        Bookings booking = service.createBooking(req, fetchLoggedIn(principal));
        List<BookingResponse> response = booking.getUserBookings().stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/user")
    public ResponseEntity<List<BookingResponse>> getAllBookings(Principal principal){
        String username = principal.getName();
        Users user = userService.findUserByUsername(username);

        List<UserBooking> bookings = service.getBookingsByUser(user.getId());
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{userId}/users")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable("userId") Long userId){
        List<UserBooking> bookings = service.getBookingsByUser(userId);
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/booking/{projectId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByProjectId(@PathVariable("projectId") Long projectId){
        List<UserBooking> bookings = service.getUserBookingsByProject(projectId);
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<BookingResponse>> getBookingsById(@PathVariable("bookingId") Long bookingId){
        List<UserBooking> bookings = service.getUserBookingsByProject(bookingId);
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookingId}/update")
    public ResponseEntity<List<BookingResponse>> updateBookingsByUserId(@PathVariable("bookingId") Long bookingId, @RequestBody PatchBookingRequest req, Principal principal){
        Bookings booking = service.updateBooking(bookingId,req, fetchLoggedIn(principal));
        List<BookingResponse> response = booking.getUserBookings().stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/respond")
    public ResponseEntity<Void> respondBooking(@RequestBody PatchUserBookingRequest req){
        service.updateUserBooking(req.getBookingId(), req.getUserId(), String.valueOf(req.getStatus()));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookingId}/user/{userId}/delete")
    public ResponseEntity<Void> deleteUserBooking(@PathVariable("bookingId") Long bookingId, @PathVariable("userId") Long userId){
        service.removeUserBooking(bookingId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteBooking(Principal principal, @PathVariable("id") Long id){
        Long userId = userService.findUserByUsername(principal.getName()).getId();
        service.removeBooking(id,userId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{projectId}/available")
    public ResponseEntity<List<UserProjectResponse>> getAvailableUsers(@PathVariable("projectId") Long projectId,@RequestParam("date") Long dateMillis){
        List<UserProjectResponse> available = service.getAvailable(projectId, dateMillis);
        return ResponseEntity.ok(available);
    }

    private Long fetchLoggedIn(Principal p){
        String username = p.getName();
        return userService.findUserByUsername(username).getId();
    }



}


