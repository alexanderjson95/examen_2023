package com.example.backend.model.Bookings;

import com.example.backend.ToExport;
import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.ProjectResponse;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookingResponse {

    private Long bookingId;
    private Long projectId;
    private String projectName;
    private Long userId;
    private String firstName;
    private String lastName;
    private boolean accepted;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;
    private Long dateMillis;
    private boolean availability;
    private LocalDateTime created;


    public static BookingResponse fromUserBooking(UserBooking userBooking) {
        return BookingResponse.builder()
                .bookingId(userBooking.getBooking().getId())
                .projectId(userBooking.getProject().getId())
                .projectName(userBooking.getProject().getProjectName())
                .userId(userBooking.getUser().getId())
                .firstName(userBooking.getUser().getFirstName())
                .lastName(userBooking.getUser().getLastName())
                .accepted(userBooking.isAccepted())
                .startHour(userBooking.getBooking().getStartHour())
                .startMinute(userBooking.getBooking().getStartMinute())
                .endHour(userBooking.getBooking().getEndHour())
                .endMinute(userBooking.getBooking().getEndMinute())
                .dateMillis(userBooking.getBooking().getDateMillis())
                .availability(userBooking.isAvailability())
                .created(userBooking.getBooking().getCreated())
                .build();
    }

}
