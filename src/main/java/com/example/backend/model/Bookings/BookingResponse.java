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
    private String bookingTitle;
    private String bookingDescription;
    private Long userId;
    private String firstName;
    private String lastName;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;
    private Long dateMillis;
    private BookingStatusType status;
    private LocalDateTime created;


    public static BookingResponse fromUserBooking(UserBooking userBooking) {
        return BookingResponse.builder()
                .bookingId(userBooking.getBooking().getId())
                .projectId(userBooking.getProject().getId())
                .projectName(userBooking.getProject().getProjectName())
                .bookingTitle(userBooking.getBooking().getBookingTitle())
                .bookingDescription(userBooking.getBooking().getBookingDescription())
                .userId(userBooking.getUser().getId())
                .firstName(userBooking.getUser().getFirstName())
                .lastName(userBooking.getUser().getLastName())
                .startHour(userBooking.getBooking().getStartHour())
                .startMinute(userBooking.getBooking().getStartMinute())
                .endHour(userBooking.getBooking().getEndHour())
                .endMinute(userBooking.getBooking().getEndMinute())
                .dateMillis(userBooking.getBooking().getDateMillis())
                .status(userBooking.getStatus())
                .created(userBooking.getBooking().getCreated())
                .build();
    }

}
