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
    private Long dateMillis;

    @NotNull(message = "Starttid saknas")
    private Integer startHour;
    @NotNull(message = "Starttid saknas")
    private Integer startMinute;
    @NotNull(message = "Sluttid saknas")
    private Integer endHour;
    @NotNull(message = "Sluttid saknas")
    private Integer endMinute;

    @CreationTimestamp
    private LocalDateTime created;
}


