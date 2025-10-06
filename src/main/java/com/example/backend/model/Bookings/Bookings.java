package com.example.backend.model.Bookings;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date_millis")
    private Long dateMillis;

    @NotNull(message = "Starttid saknas")
    private Integer startHour;
    @NotNull(message = "Starttid saknas")
    private Integer startMinute;
    @NotNull(message = "Sluttid saknas")
    private Integer endHour;
    @NotNull(message = "Sluttid saknas")
    private Integer endMinute;

    @Column(name = "booking_title")
    private String bookingTitle;
    @Column(name = "booking_description")
    private String bookingDescription;

    // cascade = tar med hela objektet i sql flödet, inte bara id
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBooking> userBookings = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created;
}


