package com.example.backend;

import com.example.backend.model.Bookings.*;
import com.example.backend.model.Projects.*;
import com.example.backend.model.Users.Users;
import com.example.backend.service.ProjectService;
import com.example.backend.service.UserBookingService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final UserBookingService service;
    private final UserService userService;



    @PostMapping()
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest req){
        service.createBooking(req, req.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


    @GetMapping()
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
        System.out.println("Running this one with id" + userId);
        List<UserBooking> bookings = service.getBookingsByUser(userId);
        List<BookingResponse> response = bookings.stream()
                .map(BookingResponse::fromUserBooking)
                .toList();
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{bookingId}/update")
    public ResponseEntity<BookingResponse> updateBookingsByUserId(@PathVariable("bookingId") Long bookingId, @RequestBody PatchBookingRequest req){
        System.out.println("changing booking this one with id changingchangingchangingchangingchanging" + bookingId);
        service.updateBooking(bookingId, req);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


