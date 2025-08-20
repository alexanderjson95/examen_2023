package com.example.backend.model.Projects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


/**
 * DTO för projekt
 */

@Getter
@Setter
public class ProjectRequest {
    private Long userId;
    @NotNull
    @Size(max = 50)
    private String projectName;
    @Size(max = 100)
    private String description;
    private Boolean isPublic;
    private double salary = 0.0;
    private String type;
}
