package com.example.backend.model.Projects;


import com.example.backend.ToExport;
import lombok.*;

import java.time.LocalDateTime;
@ToExport

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String projectName;
    private String description;
    private boolean isPublic;
    private double salary;
    private String type;
    private LocalDateTime created;

    public static ProjectResponse fromProject(Project project) {
        return new ProjectResponse (
                project.getId(),
                project.getProjectName(),
                project.getDescription(),
                project.isPublic(),
                project.getSalary(),
                project.getGenre(),
                project.getCreated()
        );
    }
}
