package com.example.backend.model.Bookings;


import com.example.backend.model.Projects.Project;
import com.example.backend.model.Users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Har en user_booking för seperation, men också för att i framtid kunna lägga på flera användare på samma bokningsID enklare
 * Så en bokning med id 3 kan ha X antal user_bookings anslutet till sig, varje userbooking har unik data relevant till användaren,
 * medans själva booking endast innehåller data relevant för bokningen
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_bookings")
public class UserBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Bookings booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    private boolean accepted;
    private boolean availability;


}



