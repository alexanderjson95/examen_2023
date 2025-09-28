package com.example.backend.model.Projects;

import com.example.backend.ToExport;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


/**
 * DTO för projekt
 */
@ToExport

@Getter
@Setter
public class ProjectRequest {
    @NotNull
    @Size(max = 50)
    private String projectName;
    @Size(max = 100)
    private String description;
    private String type;
    private JoinType requestRule;

}
