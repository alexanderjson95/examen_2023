package com.example.backend;

import com.example.backend.model.Bookings.*;
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

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final UserBookingService service;
    private final UserService userService;


    @PostMapping()
    public ResponseEntity<List<BookingResponse>> createBooking(@RequestBody BookingRequest req){
        Bookings booking = service.createBooking(req);
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

    @GetMapping("/{projectId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByProjectId(@PathVariable("projectId") Long projectId){
        List<UserBooking> bookings = service.getUserBookingsByProject(projectId);
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookingId}/update")
    public ResponseEntity<List<BookingResponse>> updateBookingsByUserId(@PathVariable("bookingId") Long bookingId, @RequestBody PatchBookingRequest req){
        Bookings booking = service.updateBooking(bookingId,req);
        List<BookingResponse> response = booking.getUserBookings().stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/respond")
    public ResponseEntity<UserBooking> respondBooking(Principal principal, @RequestBody PatchUserBookingRequest req){
        Long userId = userService.findUserByUsername(principal.getName()).getId();
        UserBooking ub = service.updateUserBooking(req.getBookingId(), userId, String.valueOf(req.getStatus()));
        return ResponseEntity.status(HttpStatus.OK).body(ub);
    }

    @PatchMapping("/{userBookingId}/delete")
    public ResponseEntity<UserBooking> respondBooking(Principal principal, @PathVariable("id") Long id){
        Long userId = userService.findUserByUsername(principal.getName()).getId();
        service.removeUserBooking(id);
        return ResponseEntity.noContent().build();
    }


}


